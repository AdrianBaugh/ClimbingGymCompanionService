package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllArchivedRoutesRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllArchivedRoutesResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetAllArchivedRoutesActivity for the ClimbingGymCompanionService API.
 * <p>
 * This API allows the customer to get the list of all the archived routes the gym has.
 */
public class GetAllArchivedRoutesActivity {

    private final Logger log = LogManager.getLogger();
    private final RouteDao routeDao;

    /**
     * Instantiates a new GetAllActiveRoutesActivity object.
     *
     * @param routeDao RouteDao to access the routes table.
     */
    @Inject
    public GetAllArchivedRoutesActivity(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request by retrieving all the archived routes for the gym from the database.
     * <p>
     * It then returns the routes list.
     * <p>
     * If no route does not exist, this should throw a RouteNotFoundException.
     *
     * @param getAllArchivedRoutesRequest request object containing the status to look up routes for.
     * @return GetAllArchivedRoutesResult result object containing the route's list of API-defined RouteModels.
     */
    public GetAllArchivedRoutesResult handleRequest(final GetAllArchivedRoutesRequest getAllArchivedRoutesRequest) {
        log.info("Received GetAllArchivedRoutesRequest {}", getAllArchivedRoutesRequest);

        List<Route> archivedRoutes = routeDao.getAllArchivedRoutes(getAllArchivedRoutesRequest.getIsArchived());

        List<Route> sorted = new ArrayList<>(archivedRoutes);
        Collections.sort(sorted, Comparator.comparing(Route::getLocation));

        List<RouteModel> routeModels = new ModelConverter().toRouteModelList(sorted);

        return GetAllArchivedRoutesResult.builder()
                .withRouteList(routeModels)
                .build();
    }

}
