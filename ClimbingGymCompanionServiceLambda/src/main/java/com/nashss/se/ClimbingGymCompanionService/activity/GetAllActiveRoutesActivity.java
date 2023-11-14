package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllActiveRoutesRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllActiveRoutesResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import javax.inject.Inject;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the GetAllActiveRoutesActivity for the ClimbingGymCompanionService API.
 * <p>
 * This API allows the customer to get the list of all the current active routes the gym has.
 */
public class GetAllActiveRoutesActivity {

    private final Logger log = LogManager.getLogger();
    private final RouteDao routeDao;

    /**
     * Instantiates a new GetAllActiveRoutesActivity object.
     *
     * @param routeDao RouteDao to access the routes table.
     */
    @Inject
    public GetAllActiveRoutesActivity(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request by retrieving all the active routes for the gym from the database.
     * <p>
     * It then returns the routes list.
     * <p>
     * If no route does not exist, this should throw a RouteNotFoundException.
     *
     * @param getAllActiveRoutesRequest request object containing the status to look up routes for.
     * @return GetAllActiveRoutesResult result object containing the route's list of API-defined RouteModels.
     */
    public GetAllActiveRoutesResult handleRequest(final GetAllActiveRoutesRequest getAllActiveRoutesRequest) {
        log.info("Received GetAllActiveRoutesRequest {}", getAllActiveRoutesRequest);

        List<Route> activeRoutes = routeDao.getAllActiveRoutes(getAllActiveRoutesRequest.getIsArchived());
        List<RouteModel> routeModels = new ModelConverter().toRouteModelList(activeRoutes);

        return GetAllActiveRoutesResult.builder()
                .withRouteList(routeModels)
                .build();
    }

}
