# Backend

_This repo only contains source code for backend service, for UI/frontend, please refer to [UI](../Frontend/)_

# Live path

Currently, this service is deployed on AWS using ECS service with Fargate.

`52.32.116.87:9000/health-check`

Example response:
![image](https://user-images.githubusercontent.com/83547917/232935615-cb7391ae-bbf1-4225-9d6d-3ab73e99746d.png)

The docker image is pubilished on [DockerHub](https://hub.docker.com/repository/docker/nickwangdockerdev/one-two-three-backend/general)

# Endpoints

| Method | Endpoint                | Params                                         |
| ------ | ----------------------- | ---------------------------------------------- |
| GET    | /health-check           | null                                           |
| GET    | /active-days            | String personId                                |
| GET    | /most-steps             | String personId, String date, String daysAfter |
| GET    | /all-steps              | String personId, String date, String daysAfter |
| GET    | /longest-trips          | String personId, String date, String daysAfter |
| GET    | /most-visited-places    | String personId, String date, String daysAfter |
| GET    | /longest-visited-places | String personId, String date, String daysAfter |

# Set up

1. Installed Apache Maven, version at least `3.8.1`.

   You can download from [Here](https://maven.apache.org/download.cgi)

2. Please use `corretto-11` as your JDK.

   You can download from [Here](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html). Or you can use `Intellij` to download this JDK.

3. Please use [this link](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html) to download DynamoDB local version.

4. Please use [this link](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) to download AWS CLI.

   After downloading AWS CLI, you can use below command to generate a config file to run DynamoDB locally.

   ```bash
   $ aws configure

   AWS Access Key ID [None]: dummy
   AWS Secret Access Key [None]: dummy
   Default region name [None]: us-west-2
   Default output format [None]: json
   ```

5. Please install docker on your machine.

# Build

Please use `mvn spring-boot:build-image` to build the project

# Database

This project implemented DynamoDB. Please contact us to get an IAM user to access the database.
The database is in US-WEST-2 region.

![image](https://user-images.githubusercontent.com/83547917/232936625-4725d120-1b56-4a1e-93b1-3b81e8573919.png)

# Load Data Locally

Please make sure you run the DynamoDB first before load the data.

`$ java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar`

Running result will looks like

![](C:\Users\nicho\AppData\Roaming\marktext\images\2023-02-13-18-20-17-image.png)

After that, you can use download the helper method from `https://github.com/CS5500-2023SP/DevHelper` and

run main method defined in `localDevHelper/CreateTableAndImportData.java` to load data.

You can open a new terminal and use below commands to test whether the data has been imported.

`$ aws dynamodb list-tables --endpoint-url http://localhost:8000`

Expect result should looks like

```json
{
  "TableNames": ["PersonActivity"]
}
```

And you can use `get-item` to get some result from `PersonActivity`

`$ aws dynamodb get-item --consistent-read --table-name PersonActivity --key '{"personID":{"S": "1"}, "dateID":{"S": "20130209"}}' --endpoint-url http://localhost:8000`

Expect result shoud looks like

```json
{
  "Item": {
    "dateID": {
      "S": "20130209"
    },
    "summary": {
      "S": "[{\"activity\":\"walking\",......}]"
    },
    "personID": {
      "S": "1"
    },
    "caloriesIdle": {
      "S": "1439"
    },
    "lastUpdate": {
      "S": "20150402T225557Z"
    },
    "segments": {
      "S": "[{\"type\":\"place\", .......}]"
    }
  }
}
```

And you can use command `describe-table` to get the schema of this table
`$ aws dynamodb describe-table --table-name PersonActivity --endpoint-url http://localhost:8000`
Expect result:

```json
{
  "Table": {
    "AttributeDefinitions": [
      {
        "AttributeName": "personID",
        "AttributeType": "S"
      },
      {
        "AttributeName": "dateID",
        "AttributeType": "S"
      }
    ],
    "TableName": "PersonActivity",
    "KeySchema": [
      {
        "AttributeName": "personID",
        "KeyType": "HASH"
      },
      {
        "AttributeName": "dateID",
        "KeyType": "RANGE"
      }
    ],
    "TableStatus": "ACTIVE",
    "CreationDateTime": "2023-02-14T14:01:42.585000-08:00",
    "ProvisionedThroughput": {
      "LastIncreaseDateTime": "1969-12-31T16:00:00-08:00",
      "LastDecreaseDateTime": "1969-12-31T16:00:00-08:00",
      "NumberOfDecreasesToday": 0,
      "ReadCapacityUnits": 10,
      "WriteCapacityUnits": 10
    },
    "TableSizeBytes": 10570700,
    "ItemCount": 1644,
    "TableArn": "arn:aws:dynamodb:ddblocal:000000000000:table/PersonActivity"
  }
}
```

If you'd like to delete the table, you can use

`$ aws dynamodb delete-table --table-name PersonActivity --endpoint-url http://localhost:8000`

# Test Coverage

![image](https://user-images.githubusercontent.com/83547917/232936819-6f822446-726a-4430-ba34-f840d756f020.png)
