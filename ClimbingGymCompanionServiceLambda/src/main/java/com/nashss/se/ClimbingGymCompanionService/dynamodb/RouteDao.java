package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
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
     * @param routeStatus is the status we wish to not return
     * @return list of nonArchived routes
     */
    public List<Route> getAllActiveRoutes(String routeStatus) {
        log.info("Entered RouteDao getAllActiveRoutes() ");

        //delete after its working
        System.out.println("************Entered RouteDao getAllActiveRoutes()************** ");
        System.out.println("************status passed in from url query parameter: " + routeStatus + " ************** ");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":statusValue", new AttributeValue().withS(routeStatus));

//        expressionAttributeValues.put(":maintenanceStatusValue", new AttributeValue().withS(RouteStatus.CLOSED_MAINTENANCE.name()));
//        expressionAttributeValues.put(":tournamentStatusValue", new AttributeValue().withS(RouteStatus.CLOSED_TOURNAMENT.name()));

//        expressionAttributeValues.put(":isArchivedValue", new AttributeValue().withBOOL(true));

        DynamoDBQueryExpression<Route> queryExpression = new DynamoDBQueryExpression<Route>()
                .withIndexName("RoutesByStatusIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("routeStatus = :statusValue")
//                .withKeyConditionExpression("routeStatus = :activeStatusValue and routeStatus = :maintenanceStatusValue and routeStatus = :tournamentStatusValue")

                //isArchived does NOT equal true
//                .withFilterExpression("isArchived = :isArchivedValue")
                .withExpressionAttributeValues(expressionAttributeValues);

        PaginatedQueryList<Route> routes = dynamoDbMapper.query(Route.class, queryExpression);

        return new ArrayList<>(routes);
    }
}
