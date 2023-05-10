package cs5500sp23.application.project.model.DAO;

import cs5500sp23.application.project.model.ConnectionManager;
import cs5500sp23.application.project.model.model.ActiveDaysEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

public class ActiveDaysDAO {

  private static ActiveDaysDAO instance = null;
  protected ConnectionManager connectionManager;

  protected ActiveDaysDAO() {
    connectionManager = new ConnectionManager();
  }

  public static ActiveDaysDAO getInstance() {
    if (instance == null) {
      instance = new ActiveDaysDAO();
    }
    return instance;
  }


  /**
   * getAllActiveDays will return all active dates for a given user id
   *
   * @param id - the person in question
   * @return all active dates
   */
  public ActiveDaysEntry getAllActiveDays(String id) {
    ArrayList<String> result = new ArrayList<>();
    QueryResponse response = queryGetAllActiveDays(id);
    for (Map<String, AttributeValue> item : response.items()) {
      result.add(item.get("dateID").s());
    }
    return new ActiveDaysEntry(result);
  }


  /**
   * Helper method, get all active days from the dynamodb
   * @param id - the person in question
   * @return - all active days of the given person
   */
  private QueryResponse queryGetAllActiveDays(String id) {
    DynamoDbClient client = null;
    QueryResponse response;
    try {
      client = this.connectionManager.getClient();
      Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
      expressionAttributeValues.put(":personID", AttributeValue.builder().s(id).build());

      QueryRequest queryRequest = QueryRequest.builder()
          .tableName("PersonActivity")
          .keyConditionExpression("personID = :personID")
          .expressionAttributeValues(expressionAttributeValues)
          .scanIndexForward(true).projectionExpression("dateID")
          .build();
      response = client.query(queryRequest);
    } catch (DynamoDbException exception) {
      throw new RuntimeException(exception);
    } finally {
      if (client != null) {
        client.close();
      }
    }
    return response;
  }
}
