package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateRouteResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.ArchivedStatus;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;
import com.nashss.se.ClimbingGymCompanionService.utils.IdUtils;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateRouteActivity {
    // idea is that a gym admin account can add the routes initially and then the routes only need
    // to be updated. Updates will most likely be done be the users themselves

    private final Logger log = LogManager.getLogger();
    private final RouteDao routeDao;
    /**
     * Instantiates a new CreateRouteActivity object.
     *
     * @param routeDao to access the routes table.
     */
    @Inject
    public CreateRouteActivity(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request by persisting a new route
     * with the provided route metadata from the request.
     * <p>
     * It then returns the newly created route with a constructed routeId.
     * <p>
     *
     * @param createRouteRequest request an object containing the route metadata
     *                              associated with it
     * @return CreateRouteResult a result object containing the API defined {@link RouteModel}
     */
    public CreateRouteResult handleRequest(final CreateRouteRequest createRouteRequest) {
        System.out.println("************ CREATE ROUTE ACTIVITY STARTED ********");
        System.out.println("************ CREATE ROUTE REQUEST: " + createRouteRequest.toString() +"********");

        log.info("received CreateRouteRequest {}", createRouteRequest);

        String location = createRouteRequest.getLocation();
        String color = createRouteRequest.getColor();
        LocalDate date = LocalDate.now();

        Route newRoute = new Route();
        newRoute.setRouteId(IdUtils.generateRouteId(location, color, date));
        newRoute.setLocation(location);
        newRoute.setColor(color);
        newRoute.setDateCreated(date);
        newRoute.setIsArchived(ArchivedStatus.FALSE.name());
        newRoute.setRouteStatus(createRouteRequest.getRouteStatus());
        newRoute.setType(createRouteRequest.getType());
        newRoute.setDifficulty(createRouteRequest.getDifficulty());
        newRoute.setRating(null);
        newRoute.setPictureKey(createRouteRequest.getPictureKey());
        newRoute.setNotesList(new ArrayList<>());

        routeDao.saveRoute(newRoute);

        RouteModel routeModel = new ModelConverter().toRouteModel(newRoute);
        System.out.println("************ CREATE ROUTE ACTIVITY ENDED ********");

        return CreateRouteResult.builder()
                .withRoute(routeModel)
                .build();
    }
}
