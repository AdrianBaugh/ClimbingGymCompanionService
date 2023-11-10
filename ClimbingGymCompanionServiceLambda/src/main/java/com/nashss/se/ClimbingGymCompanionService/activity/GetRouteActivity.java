package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetRouteResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
      * Implementation of the GetRouteActivity for the ClimbingGymCompanionService API.
      * This API allows the customer to get info on a single route from the gym.
      */
public class GetRouteActivity {
    private final Logger log = LogManager.getLogger();
    private final RouteDao routeDao;

    /**
     * Instantiates a new GetAllActiveRoutesActivity object.
     * @param routeDao RouteDao to access the routes table.
     */
    @Inject
    public GetRouteActivity(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request by retrieving a single route object for the gym from the database.
     * It then returns the routes list.
     * If no route does not exist, this should throw a RouteNotFoundException.
     *
     * @param getRouteRequest request object containing the status to look up routes for
     * @return GetRouteResult result object containing the route API-defined RouteModel
     */
    public GetRouteResult handleRequest(final GetRouteRequest getRouteRequest) {
        log.info("Received GetAllActiveRoutesRequest {}", getRouteRequest);

        Route route = routeDao.getRouteById(getRouteRequest.getRouteId());
        RouteModel routeModel = new ModelConverter().toRouteModel(route);

        return GetRouteResult.builder()
                .withRoute(routeModel)
                .build();
    }
}
