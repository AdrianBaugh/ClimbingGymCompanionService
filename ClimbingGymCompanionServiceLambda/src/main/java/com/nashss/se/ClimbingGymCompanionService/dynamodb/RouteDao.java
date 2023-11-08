package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

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
     * @param excludedRouteStatus is the status we wish to not return
     * @return list of nonArchived routes
     */
    public List<Route> getAllActiveRoutes(String excludedRouteStatus) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":statusValue", new AttributeValue().withS(excludedRouteStatus));

        DynamoDBQueryExpression<Route> queryExpression = new DynamoDBQueryExpression<Route>()
                .withIndexName("RoutesByStatusIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("routeStatus <> :statusValue")
                .withExpressionAttributeValues(expressionAttributeValues);

        PaginatedQueryList<Route> routes = dynamoDbMapper.query(Route.class, queryExpression);

        return new ArrayList<>(routes);
    }
}
