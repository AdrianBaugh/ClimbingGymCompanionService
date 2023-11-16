package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import javax.inject.Inject;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClimbDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates a ClimbDao object
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the pets table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public ClimbDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Saves a new climb to the database
     *
     *  @param climb The climb to save.
     *  @return The climb object that was saved.
     */
    public Climb saveClimb(Climb climb) {
        this.dynamoDbMapper.save(climb);
        log.info("Climb: {}, saved to the database", climb);
        return climb;
    }
}
