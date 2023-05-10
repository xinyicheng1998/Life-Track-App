package localDevHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class CreateTableAndImportData {

  public static void main(String[] args) {
    // Initialize a new local db client
    DynamoDbClient client = DynamoDbClient.builder()
        .endpointOverride(URI.create("http://localhost:8000"))
        // The region is meaningless for local DynamoDb but required for client builder validation
        .region(Region.US_WEST_2)
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create("dummy", "dummy")))
        .build();

    String file = "storyline.json";
    //String file = "E:\\5500project\\Project\\storyline.json";
    try {
      createTable(client);
    } catch (Exception ignored) {
      System.out.println("Error when creating table");
    }
    try {
      importData(client, file);
    } catch (IOException ignored) {
      System.out.println("Error when importing data");
    }
    client.close();
  }

  public static void createTable(DynamoDbClient client) {
    CreateTableRequest request = CreateTableRequest.builder().
        attributeDefinitions(
            AttributeDefinition.builder().
                attributeName("personID").attributeType(ScalarAttributeType.S).build(),
            AttributeDefinition.builder().
                attributeName("dateID").attributeType(ScalarAttributeType.S).build()).
        keySchema(
            KeySchemaElement.builder().attributeName("personID").keyType(KeyType.HASH).build(),
            KeySchemaElement.builder().attributeName("dateID").keyType(KeyType.RANGE).build()).
        provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L)
            .writeCapacityUnits(10L).build())
        .tableName("PersonActivity")
        .build();

    DynamoDbWaiter dbWaiter = client.waiter();
    String newTable = "";
    try {
      CreateTableResponse response = client.createTable(request);
      DescribeTableRequest tableRequest = DescribeTableRequest.builder()
          .tableName("PersonActivity")
          .build();

      // Wait until the Amazon DynamoDB table is created.
      WaiterResponse<DescribeTableResponse> waiterResponse = dbWaiter.waitUntilTableExists(
          tableRequest);
      waiterResponse.matched().response().ifPresent(System.out::println);
      newTable = response.tableDescription().tableName();
    } catch (DynamoDbException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    System.out.println("Created an Amazon DynamoDB table " + newTable);
  }

  public static void importData(DynamoDbClient client, String file) throws IOException {
    String json = new String(Files.readAllBytes(new File(file).toPath()));

    // Read the JSON file
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(new File(file));

    // Iterate over each activity
    for (JsonNode activity : jsonNode) {
      // Prepare the item to be put in the table
      Map<String, AttributeValue> item = new HashMap<>();
      item.put("personID", AttributeValue.builder().s("1").build());
      item.put("dateID", AttributeValue.builder().s(activity.get("date").asText()).build());
      item.put("summary", AttributeValue.builder().s(activity.get("summary").toString()).build());
      item.put("segments", AttributeValue.builder().s(activity.get("segments").toString()).build());
      item.put("caloriesIdle", AttributeValue.builder().s(activity.get("caloriesIdle").asText()).build());
      item.put("lastUpdate", AttributeValue.builder().s(activity.get("lastUpdate").asText()).build());
      // Put the item in the table
      PutItemRequest request = PutItemRequest.builder().tableName("PersonActivity").item(item).build();
      client.putItem(request);
    }
  }
}
