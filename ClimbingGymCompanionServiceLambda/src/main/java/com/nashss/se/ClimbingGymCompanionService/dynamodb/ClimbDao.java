package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.exceptions.ClimbNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsConstants;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class ClimbDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates a ClimbDao object.
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the pets table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public ClimbDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Saves a new climb to the database.
     *
     *  @param climb The climb to save.
     *  @return The climb object that was saved.
     */
    public Climb saveClimb(Climb climb) {
        this.dynamoDbMapper.save(climb);
        log.info("Climb: {}, saved to the database", climb);
        return climb;
    }

    /**
     * Retrieves a Climb by its IDs.
     *
     * @param climbId The ID of the climb to retrieve.
     * @param userId the id of the user
     * @return The Climb object if found, or throws exception if not found.
     */
    public Climb getClimbById(String climbId, String userId) {
        Climb climb = dynamoDbMapper.load(Climb.class, userId, climbId);
        if (climb == null) {
            metricsPublisher.addCount(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT, 1);
            throw new ClimbNotFoundException(String.format(
                    "Climb not found for climbId: %s for user: %s", climbId, userId));
        }
        metricsPublisher.addCount(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT, 0);
        return climb;
    }

    /**
     *
     * @param userId for to retrieve the climbs from
     * @return list of climbs from the user
     */
    public List<Climb> getAllUsersClimbs(String userId) {
        log.info("Entered ClimbDao getAllUsersClimbs() ");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":userIdValue", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<Climb> queryExpression = new DynamoDBQueryExpression<Climb>()
                .withIndexName("ClimbsByUserIdIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userIdValue")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDbMapper.query(Climb.class, queryExpression);
    }

    /**
     * Deletes a climb from the climb table.
     * @param userId hash key for climb
     * @param climbId sort key for climb
     * @return true if successful or throws an exception otherwise
     */
    public boolean deleteClimb(String userId, String climbId) {
        Climb climbToDelete = new Climb();

        climbToDelete.setUserId(userId);
        climbToDelete.setClimbId(climbId);

        try {
            dynamoDbMapper.delete(climbToDelete);
            metricsPublisher.addCount(MetricsConstants.DELETECLIMB_CLIMBNOTFOUND_COUNT, 0);
            return true;
        } catch (Exception e) {
            metricsPublisher.addCount(MetricsConstants.DELETECLIMB_CLIMBNOTFOUND_COUNT, 1);
            throw new ClimbNotFoundException(e);
        }
    }

    /**
     *
     * @param routeId to get all climbs for
     * @return list of climbs
     */
    public List<Climb> getAllClimbsByRouteId(String routeId) {
        log.info("Entered climbDao getAllClimbsByRouteId() ");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":routeIdValue", new AttributeValue().withS(routeId));

        DynamoDBQueryExpression<Climb> queryExpression = new DynamoDBQueryExpression<Climb>()
                .withIndexName("ClimbsByRouteIdIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("routeId = :routeIdValue")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDbMapper.query(Climb.class, queryExpression);
    }
}
