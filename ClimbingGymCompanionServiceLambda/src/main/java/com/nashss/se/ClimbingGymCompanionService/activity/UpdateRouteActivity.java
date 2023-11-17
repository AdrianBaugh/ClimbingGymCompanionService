package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateRouteResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.ArchivedStatus;
import com.nashss.se.ClimbingGymCompanionService.enums.RouteStatus;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import javax.inject.Inject;

public class UpdateRouteActivity {
    private final Logger log = LogManager.getLogger();
    private final RouteDao routeDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Update route activity.
     * @param routeDao the routeDao to access the database
     * @param metricsPublisher for metrics
     */
    @Inject
    public UpdateRouteActivity(RouteDao routeDao, MetricsPublisher metricsPublisher) {
        this.routeDao = routeDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request to update a route's status
     * with the provided route metadata from the request.
     * <p>
     * It then returns the updated route with a constructed routeId.
     * <p>
     *
     * @param updateRouteRequest request an object containing the route metadata
     *                              associated with it
     * @return UpdateRouteResult a result object containing the API defined {@link RouteModel}
     */
    public UpdateRouteResult handleRequest(final UpdateRouteRequest updateRouteRequest) {
        log.info("Received the Update RouteRequest {}", updateRouteRequest);

        Route route = routeDao.getRouteById(updateRouteRequest.getRouteId());

        String newStatus = updateRouteRequest.getRouteStatus();
        route.setRouteStatus(newStatus);
        if (Objects.equals(newStatus, RouteStatus.ARCHIVED.name())) {
            route.setIsArchived(ArchivedStatus.TRUE.name());
        }

        route = routeDao.saveRoute(route);

        return UpdateRouteResult.builder()
                .withRoute(new ModelConverter().toRouteModel(route))
                .build();
    }
}
