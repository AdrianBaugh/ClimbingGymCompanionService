package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.ArchivedStatus;
import com.nashss.se.ClimbingGymCompanionService.exceptions.ArchivedStatusNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.exceptions.RouteNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsConstants;
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

    /**
     * Retrieves a Route by its IDs.
     *
     * @param routeId The ID of the route to retrieve.
     * @return The Route object if found, or throws exception if not found.
     */
    public Route getRouteById(String routeId) {
        Route route = dynamoDbMapper.load(Route.class, routeId);
        if (route == null) {
            metricsPublisher.addCount(MetricsConstants.GETROUTE_ROUTENOTFOUND_COUNT, 1);
            throw new RouteNotFoundException("Route not found");
        }
        metricsPublisher.addCount(MetricsConstants.GETROUTE_ROUTENOTFOUND_COUNT, 0);
        return route;
    }

    /**
     * Saves a new route to the database
     *
     *  @param newRoute The route to save.
     *  @return The Route object that was saved.
     */
    public Route saveRoute(Route newRoute) {
        this.dynamoDbMapper.save(newRoute);
        return newRoute;
    }
}
