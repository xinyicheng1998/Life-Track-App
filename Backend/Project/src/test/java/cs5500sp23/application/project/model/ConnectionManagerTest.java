package cs5500sp23.application.project.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class ConnectionManagerTest {

  @Test
  public void testGetClient() {
    ConnectionManager connectionManager = new ConnectionManager();
    DynamoDbClient client = connectionManager.getClient();
    assertNotNull(client);
    connectionManager.closeClient(client);
  }

}