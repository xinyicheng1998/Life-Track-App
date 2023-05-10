package cs5500sp23.application.project.model.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs5500sp23.application.project.model.ConnectionManager;
import cs5500sp23.application.project.model.model.ActivitySummary;
import cs5500sp23.application.project.model.model.ActivityType;
import cs5500sp23.application.project.model.model.AllStepEntry;
import cs5500sp23.application.project.model.model.EntryKey;
import cs5500sp23.application.project.model.model.LongestTripEntry;
import cs5500sp23.application.project.model.model.LongestVisitedPlaceEntry;
import cs5500sp23.application.project.model.model.MostStepEntry;
import cs5500sp23.application.project.model.model.MostVisitedPlaceEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

/**
 * DAO object for activities inside summary filed
 */
public class ActivitySummaryDAO {

    // Single pattern: instantiation is limited to one object.
    private static ActivitySummaryDAO instance = null;
    protected ConnectionManager connectionManager;
    private LocalDate curDate;

    protected ActivitySummaryDAO() {
        connectionManager = new ConnectionManager();
    }

    public static ActivitySummaryDAO getInstance() {
        if (instance == null) {
            instance = new ActivitySummaryDAO();
        }
        return instance;
    }

    /**
     * getMostStepsInRangeByPersonID will query dynamoDB
     *
     * @param personID  - the person in question
     * @param date      - start date
     * @param daysAfter - total range
     * @return - most steps of the given person on the given range and start date
     */
    public MostStepEntry getMostStepsInRangeByPersonID(String personID, LocalDate date,
        long daysAfter) {
        MostStepEntry response = null;
        try {
            // Execute the query
            QueryResponse result = getActivitiesInRangeByPersonID(personID, date, daysAfter);

            // Build result from query response
            response = buildResultForMostStepsInRange(result);

        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
        return response;
    }

    /**
     * Helper method, handle the logic of comparing most steps
     *
     * @param result - query result got from database
     * @return - most steps of the given person on the given range and start date
     * @throws JsonProcessingException - if can't be marshalled/unmarshalled
     */
    private MostStepEntry buildResultForMostStepsInRange(QueryResponse result)
        throws JsonProcessingException {
        int maxSteps = Integer.MIN_VALUE;
        MostStepEntry mostStepResult = null;

        for (Map<String, AttributeValue> item : result.items()) {
            LocalDate curDate = LocalDate.parse((item.get(EntryKey.DATE_ID.getKey())).s(),
                DateTimeFormatter.ofPattern("yyyyMMdd"));
            List<ActivitySummary> curActivities = new ArrayList<>();
            int curSteps = 0;
            double curCal = 0;
            ObjectMapper mapper = new ObjectMapper();
            AttributeValue itemSummary = item.get(EntryKey.SUMMARY.getKey());
            List<Map<String, String>> activities = mapper.readValue(itemSummary.s(),
                new TypeReference<>() {
                });
            for (Map<String, String> ac : activities) {
                ActivityType at = ActivityType.buildType(ac.get("activity"));
                if (at != null) {
                    switch (at) {
                        case WALKING:
                        case RUNNING:
                            ActivitySummary curActivitySummary = new ActivitySummary(
                                at, ac.get("group"), Double.parseDouble(ac.get("duration")),
                                Double.parseDouble(ac.get("distance")),
                                Integer.parseInt(ac.get("steps")),
                                Double.parseDouble(ac.get("calories"))
                            );
                            curSteps += curActivitySummary.getSteps();
                            curCal += curActivitySummary.getCalories();
                            curActivities.add(curActivitySummary);
                            break;
                    }
                }
            }
            if (curSteps > maxSteps) {
                maxSteps = curSteps;
                mostStepResult = new MostStepEntry(curActivities, curDate, curSteps, curCal);
            }
        }
        return mostStepResult;
    }

    /**
     * getMostVisitedPlacesInRangeByPersonID will query dynamoDB
     *
     * @param personID  - the person in question
     * @param date      - start date
     * @param daysAfter - total range
     * @return - a map shows the visited time for each place and the place with most visited times
     */
    public MostVisitedPlaceEntry getMostVisitedPlacesInRangeByPersonID(String personID,
        LocalDate date,
        long daysAfter) {
        MostVisitedPlaceEntry result = null;

        QueryResponse response = getActivitiesInRangeByPersonID(personID, date, daysAfter);

        // Build result from query response
        try {
            result = buildResultForMostVisitedPlaceInRange(response);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }

        return result;
    }

    /**
     * Helper method, handle the logic of comparing most visited times
     *
     * @param result - query result got from database
     * @return - most visited place of the given person on the given range and start date
     * @throws JsonProcessingException - if can't be marshalled/unmarshalled
     */
    private MostVisitedPlaceEntry buildResultForMostVisitedPlaceInRange(QueryResponse result)
        throws JsonProcessingException {
        Map<String, Integer> visitedPlaces = new HashMap<String, Integer>();
        int maxVisits = Integer.MIN_VALUE;
        String mostVisitedPlace = null;

        for (Map<String, AttributeValue> item : result.items()) {
            ObjectMapper mapper = new ObjectMapper();
            AttributeValue itemSummary = item.get(EntryKey.SEGMENTS.getKey());
            List<Map<String, Object>> segments = mapper.readValue(itemSummary.s(),
                new TypeReference<>() {
                });
            for (Map<String, Object> seg : segments) {
                if (!seg.get("type").toString().equals("place")) {
                    continue;
                }
                Map<String, Object> place = mapper.convertValue(seg.get("place"), Map.class);
                if (place.get("name") == null) {
                    continue;
                }
                String name = place.get("name").toString();
                int numVisits = 0;

                if (visitedPlaces.containsKey(name)) {
                    numVisits = visitedPlaces.get(name);
                }
                numVisits += 1;
                visitedPlaces.put(name, numVisits);
                if (numVisits > maxVisits) {
                    maxVisits = numVisits;
                    mostVisitedPlace = name;
                }
            }
        }
        return new MostVisitedPlaceEntry(visitedPlaces, mostVisitedPlace);
    }

    /**
     * getLongestVisitedPlacesInRangeByPersonID will query dynamoDB
     *
     * @param personID  - the person in question
     * @param date      - start date
     * @param daysAfter - total range
     * @return - a map shows the duration (minutes) of each place and the place with longest
     * duration
     */
    public LongestVisitedPlaceEntry getLongestVisitedPlacesInRangeByPersonID(String personID,
        LocalDate date,
        long daysAfter) {
        LongestVisitedPlaceEntry result = null;

        QueryResponse response = getActivitiesInRangeByPersonID(personID, date, daysAfter);

        // Build result from query response
        try {
            result = buildResultForLongestVisitedPlaceInRange(response);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }

        return result;
    }

    /**
     * Helper method, handle the logic of comparing longest duration
     *
     * @param result - query result got from database
     * @return - longest visited place of the given person on the given range and start date
     * @throws JsonProcessingException - if can't be marshalled/unmarshalled
     */
    private LongestVisitedPlaceEntry buildResultForLongestVisitedPlaceInRange(QueryResponse result)
        throws JsonProcessingException {
        Map<String, Integer> visitedPlaces = new HashMap<String, Integer>();
        int longestVisit = Integer.MIN_VALUE;
        String longestVisitedPlace = null;

        for (Map<String, AttributeValue> item : result.items()) {
            ObjectMapper mapper = new ObjectMapper();
            AttributeValue itemSummary = item.get(EntryKey.SEGMENTS.getKey());
            List<Map<String, Object>> segments = mapper.readValue(itemSummary.s(),
                new TypeReference<>() {
                });
            for (Map<String, Object> seg : segments) {
                if (!seg.get("type").toString().equals("place")) {
                    continue;
                }
                Map<String, Object> place = mapper.convertValue(seg.get("place"), Map.class);
                if (place.get("name") == null) {
                    continue;
                }

                String start = seg.get("startTime").toString().substring(0, 15);
                String end = seg.get("endTime").toString().substring(0, 15);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
                LocalDateTime startTime = LocalDateTime.parse(start, formatter);
                LocalDateTime endTime = LocalDateTime.parse(end, formatter);
                long minutes = ChronoUnit.MINUTES.between(startTime, endTime);

                String name = place.get("name").toString();
                int durationVisit = 0;

                if (visitedPlaces.containsKey(name)) {
                    durationVisit = visitedPlaces.get(name);
                }
                durationVisit += minutes;
                visitedPlaces.put(name, durationVisit);
                if (durationVisit > longestVisit) {
                    longestVisit = durationVisit;
                    longestVisitedPlace = name;
                }
            }
        }
        return new LongestVisitedPlaceEntry(visitedPlaces, longestVisitedPlace);
    }

    /**
     * Helper method, get activities from dynamodb within time range
     *
     * @param personID  - the person in question
     * @param date      - start date
     * @param daysAfter - total range
     * @return - most steps of the given person on the given range and start date
     */
    private QueryResponse getActivitiesInRangeByPersonID(String personID, LocalDate date,
        long daysAfter) {
        DynamoDbClient client = null;
        QueryResponse response;
        LocalDate endDate = date.plusDays(daysAfter);

        try {
            // get a DynamoDB client from connection Manager
            client = this.connectionManager.getClient();

            // Build expressionAttributeValues
            Map<String, AttributeValue> expressionAttributeValues = Map.of(
                ":personID", AttributeValue.builder().s(String.valueOf(personID)).build(),
                ":start", AttributeValue.builder().s(
                    date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))).build(),
                ":end", AttributeValue.builder().s(
                    endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))).build());

            // Build query request
            QueryRequest request = QueryRequest.builder()
                .tableName("PersonActivity")
                .keyConditionExpression("#personID = :personID and dateID between :start and :end")
                .expressionAttributeValues(expressionAttributeValues)
                .expressionAttributeNames(Map.of("#personID", "personID"))
                .build();

            // Execute the query
            response = client.query(request);
        } catch (DynamoDbException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return response;
    }

    /**
     * Parse the data from json to step entry
     *
     * @param item - current item got from dynamoDB
     * @return - step entry object
     * @throws JsonProcessingException - if json processing exception happens
     */
    private MostStepEntry parseCurrentEntry(Map<String, AttributeValue> item)
        throws JsonProcessingException {
        LocalDate curDate = LocalDate.parse((item.get(EntryKey.DATE_ID.getKey())).s(),
            DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<ActivitySummary> curActivities = new ArrayList<>();
        int curSteps = 0;
        double curCal = 0;
        ObjectMapper mapper = new ObjectMapper();
        AttributeValue itemSummary = item.get(EntryKey.SUMMARY.getKey());
        List<Map<String, String>> activities = mapper.readValue(itemSummary.s(),
            new TypeReference<>() {
            });
        for (Map<String, String> ac : activities) {
            ActivityType at = ActivityType.buildType(ac.get("activity"));
            if (at != null) {
                switch (at) {
                    case WALKING:
                    case RUNNING:
                        ActivitySummary curActivitySummary = new ActivitySummary(
                            at, ac.get("group"), Double.parseDouble(ac.get("duration")),
                            Double.parseDouble(ac.get("distance")),
                            Integer.parseInt(ac.get("steps")),
                            Double.parseDouble(ac.get("calories"))
                        );
                        curSteps += curActivitySummary.getSteps();
                        curCal += curActivitySummary.getCalories();
                        curActivities.add(curActivitySummary);
                        break;
                }
            }
        }
        return new MostStepEntry(curActivities, curDate, curSteps, curCal);
    }


    /**
     * Return daily summary of all activity in given time range
     *
     * @param personID  - person ID in question
     * @param date      - start date
     * @param daysAfter - time range
     * @return daily summary of all activity in given time range
     */
    public AllStepEntry getAllStepsInRangeByPersonID(String personID, LocalDate date,
        long daysAfter) {
        AllStepEntry response = null;
        try {
            // Execute the query
            QueryResponse result = getActivitiesInRangeByPersonID(personID, date, daysAfter);

            // Build result from query response
            response = buildResultForAllActivities(result);

        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
        return response;
    }

    /**
     * Build result form current Query result
     *
     * @param result - Query result form dynamodb
     * @return - the list of step entry
     * @throws JsonProcessingException - if json processing exception happens
     */
    private AllStepEntry buildResultForAllActivities(QueryResponse result)
        throws JsonProcessingException {
        List<MostStepEntry> allSteps = new ArrayList<>();
        for (Map<String, AttributeValue> item : result.items()) {
            MostStepEntry curEntry = parseCurrentEntry(item);
            allSteps.add(curEntry);
        }
        return new AllStepEntry(allSteps);
    }

    /**
     * getLongestTripsInRangeByPersonID which takes three parameters: personID of type String, date
     * of type LocalDate, and daysAfter of type long. The method returns a LongestTripEntry object,
     * which represents the longest trip made by a person within a specified date range. The method
     * calls another method getActivitiesInRangeByPersonID which returns a QueryResponse object. The
     * method then builds a LongestTripEntry object from the QueryResponse object by iterating over
     * the segments and calculating the duration of each trip. The method returns the
     * LongestTripEntry object with the longest trip duration and the start time of the longest
     * trip.
     *
     * @param personID  - person ID in question
     * @param date      - start date
     * @param daysAfter - time range
     * @return a map shows the duration (seconds) of each start time with the longest duration
     */
    public LongestTripEntry getLongestTripsInRangeByPersonID(String personID, LocalDate date,
        long daysAfter) {
        LongestTripEntry result = null;
        QueryResponse response = getActivitiesInRangeByPersonID(personID, date, daysAfter);
        // Build result from query response
        try {
            result = buildResultForLongestTripInRange(response);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }

        return result;
    }

    /**
     * Helper method, handle the logic of comparing the longest duration
     *
     * @param result - query result got from database
     * @return longest visited place of the given person on the given range and start date
     * @throws JsonProcessingException - if you can't be marshalled/unmarshalled
     */
    private LongestTripEntry buildResultForLongestTripInRange(QueryResponse result)
        throws JsonProcessingException {
        Map<LocalDateTime, Long> trips = new HashMap<>();
        long longestTripDuration = Long.MIN_VALUE;// in seconds
        LocalDateTime startOfLongestTrip = null;

        for (Map<String, AttributeValue> item : result.items()) {
            ObjectMapper mapper = new ObjectMapper();
            AttributeValue itemSummary = item.get(EntryKey.SEGMENTS.getKey());
            List<Map<String, Object>> segments = mapper.readValue(itemSummary.s(),
                new TypeReference<>() {
                });
            for (Map<String, Object> seg : segments) {
                if (!seg.get("type").toString().equals("move")) {
                    continue;
                }
                String start = seg.get("startTime").toString().substring(0, 15);
                String end = seg.get("endTime").toString().substring(0, 15);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
                LocalDateTime startTime = LocalDateTime.parse(start, formatter);
                LocalDateTime endTime = LocalDateTime.parse(end, formatter);
                long seconds = ChronoUnit.SECONDS.between(startTime, endTime);

                trips.put(startTime, seconds);
                if (seconds > longestTripDuration) {
                    longestTripDuration = seconds;
                    startOfLongestTrip = startTime;
                }
            }
        }
        return new LongestTripEntry(trips, startOfLongestTrip, longestTripDuration);
    }

}
