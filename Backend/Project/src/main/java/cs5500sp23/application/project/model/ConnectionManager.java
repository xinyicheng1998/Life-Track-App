package cs5500sp23.application.project.model;

import java.net.URI;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class ConnectionManager {

//   Only for local dev
//  private final String URL = "http://localhost:8000";

  private final Region AWS_REGION = Region.US_WEST_2;

//  private final String ACCESS_KEY_ID = "dummy";
  private final String ACCESS_KEY_ID = "AKIAZS7KSSKKWLH5GDEO";

//  private final String SECRET_ACCESS_KEY = "dummy";
  private final String SECRET_ACCESS_KEY = "vXNCArgJOVUj4f3tsDepstk7MwBhJ/61YvYi2SuT";

  /**
   * @return a DynamoDB client instance
   */
  public DynamoDbClient getClient() {
    DynamoDbClient client = null;
    try {
      client = DynamoDbClient.builder()
//          .endpointOverride(URI.create(URL))
          // The region is meaningless for local DynamoDb but required for client builder validation
          .region(AWS_REGION)
          .credentialsProvider(StaticCredentialsProvider.create(
              AwsBasicCredentials.create(ACCESS_KEY_ID, SECRET_ACCESS_KEY)))
          .build();
    } catch (DynamoDbException e) {
      throw e;
    }
    return client;
  }

  /**
   * Close connection to the DynamoDB client
   * @param client - DynamoDB Client
   */
  public void closeClient(DynamoDbClient client) {
    try {
      client.close();
    } catch (DynamoDbException e) {
      throw e;
    }
  }
}
