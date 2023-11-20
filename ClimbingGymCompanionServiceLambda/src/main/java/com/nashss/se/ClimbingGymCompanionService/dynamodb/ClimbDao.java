package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.exceptions.RouteNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsConstants;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * @return The Route object if found, or throws exception if not found.
     */
    public Climb getClimbById(String climbId, String userId) {
        Climb climb = dynamoDbMapper.load(Climb.class, climbId, userId);
        if (climb == null) {
            metricsPublisher.addCount(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT, 1);
            throw new RouteNotFoundException(String.format(
                    "Climb not found for climbId, %s for user: %s", climbId, userId));
        }
        metricsPublisher.addCount(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT, 0);
        return climb;
    }
}
