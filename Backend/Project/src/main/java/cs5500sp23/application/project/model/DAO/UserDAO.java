package cs5500sp23.application.project.model.DAO;

import cs5500sp23.application.project.model.ConnectionManager;
import cs5500sp23.application.project.model.model.CreateUserEntry;
import cs5500sp23.application.project.model.model.DeleteUserEntry;
import cs5500sp23.application.project.model.model.UpdateUserEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class UserDAO {

    private static UserDAO instance = null;
    protected ConnectionManager connectionManager;

    /**
     * singleton design pattern to manipulate database operations
     */
    protected UserDAO() {
        connectionManager = new ConnectionManager();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public CreateUserEntry createUser(String firstName, String lastName, String email) {
        DynamoDbClient client = null;
        try {
            // get a DynamoDB client from connection Manager
            client = this.connectionManager.getClient();
            Boolean exist = checkUser(email);
            if (exist) {
                return new CreateUserEntry(false);
            }
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("personID", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
            item.put("firstName", AttributeValue.builder().s(firstName).build());
            item.put("lastName", AttributeValue.builder().s(lastName).build());
            item.put("email", AttributeValue.builder().s(email).build());
            // Put the item in the table
            PutItemRequest request = PutItemRequest.builder().tableName("Users").item(item).build();
            client.putItem(request);
        } catch (DynamoDbException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return new CreateUserEntry(true);
    }

    private Boolean checkUser(String email) {
        DynamoDbClient client = null;
        QueryResponse response;
        try {
            client = this.connectionManager.getClient();
            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":email", AttributeValue.builder().s(email).build());

            QueryRequest queryRequest = QueryRequest.builder()
                .tableName("Users")
                .keyConditionExpression("email = :email")
                .expressionAttributeValues(expressionAttributeValues)
                .scanIndexForward(true)
                .build();
            response = client.query(queryRequest);
        } catch (DynamoDbException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return !response.items().isEmpty();
    }

    /**
     * Update the user's first name and last name, according to email
     * @param newFirstName - the new first name of user
     * @param newLastName - the new last name of user
     * @param email - the email of the user, unique and primary key
     * @return whether the update operation is executed successfully
     */
    public UpdateUserEntry updateUser(String newFirstName, String newLastName, String email) {
        DynamoDbClient client = null;
        try {
            // get a DynamoDB client from connection Manager
            client = this.connectionManager.getClient();
            Boolean exist = checkUser(email);
            if (!exist) {
                return new UpdateUserEntry(false);
            }
            HashMap<String, AttributeValue> itemKey = new HashMap<>();
            itemKey.put("email", AttributeValue.builder().s(email).build());

            HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<>();
            updatedValues.put("firstName", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(newFirstName).build()).action(
                    AttributeAction.PUT).build());
            updatedValues.put("lastName", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(newLastName).build()).action(
                    AttributeAction.PUT).build());
            UpdateItemRequest request = UpdateItemRequest.builder().tableName("Users").key(itemKey)
                .attributeUpdates(updatedValues).build();
            client.updateItem(request);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return new UpdateUserEntry(true);
    }

    /**
     * Delete the user, according to email
     * @param email - the email of the user, unique and primary key
     * @return  whether the delete operation is executed successfully
     */
    public DeleteUserEntry deleteUser(String email) {
        DynamoDbClient client = null;
        try {
            client = this.connectionManager.getClient();
            Boolean exist = checkUser(email);
            if (!exist) {
                return new DeleteUserEntry(false);
            }
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put("email", AttributeValue.builder().s(email).build());
            DeleteItemRequest request = DeleteItemRequest.builder().tableName("Users").key(keyToGet)
                .build();
            client.deleteItem(request);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return new DeleteUserEntry(true);
    }

}
