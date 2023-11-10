package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.ArchivedStatus;
import com.nashss.se.ClimbingGymCompanionService.exceptions.ArchivedStatusNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouteDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates a ReservationDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the pets table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public RouteDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     *
     * @param isArchived is the archived status to return
     * @return list of nonArchived routes
     */
    public List<Route> getAllActiveRoutes(String isArchived) {
        log.info("Entered RouteDao getAllActiveRoutes() ");

        if (isArchived == null || !isArchived.equals(ArchivedStatus.FALSE.name())){
            throw new ArchivedStatusNotFoundException("Archived status: " + isArchived +
                    " does not match criteria of TRUE or FALSE");
        }

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":archivedValue", new AttributeValue().withS(isArchived));

        DynamoDBQueryExpression<Route> queryExpression = new DynamoDBQueryExpression<Route>()
                .withIndexName("RoutesByArchivedIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("isArchived = :archivedValue")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDbMapper.query(Route.class, queryExpression);
    }
}
