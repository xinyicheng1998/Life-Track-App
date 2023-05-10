package cloudDevHelper;


import java.io.IOException;
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

public class CloudCreateUserTable {

    public static void main(String[] args) {
        // Initialize a new local db client
        DynamoDbClient client = DynamoDbClient.builder()
            // The region is meaningless for local DynamoDb but required for client builder validation
            .region(Region.US_WEST_2)
            .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create("YOUR_ACCESS", "YOUR_ACCESS")))
            .build();

        try {
            createTable(client);
        } catch (Exception ignored) {
            System.out.println("Error when creating table");
        }
        try {
            importData(client);
        } catch (IOException ignored) {
            System.out.println("Error when importing data");
        }
        client.close();
    }

    public static void createTable(DynamoDbClient client) {
        CreateTableRequest request = CreateTableRequest.builder().
            attributeDefinitions(
                AttributeDefinition.builder().
                    attributeName("email").attributeType(ScalarAttributeType.S).build()).
            keySchema(
                KeySchemaElement.builder().attributeName("email").keyType(KeyType.HASH).build()).
            provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L)
                .writeCapacityUnits(10L).build())
            .tableName("Users")
            .build();

        DynamoDbWaiter dbWaiter = client.waiter();
        String newTable = "";
        try {
            CreateTableResponse response = client.createTable(request);
            DescribeTableRequest tableRequest = DescribeTableRequest.builder()
                .tableName("Users")
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

    public static void importData(DynamoDbClient client) throws IOException {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("personID", AttributeValue.builder().s("1").build());
        item.put("firstName", AttributeValue.builder().s("Ted").build());
        item.put("lastName", AttributeValue.builder().s("Smith").build());
        item.put("email", AttributeValue.builder().s("tedsmith@gmail.com").build());
        // Put the item in the table
        PutItemRequest request = PutItemRequest.builder().tableName("Users").item(item).build();
        client.putItem(request);
    }

}


