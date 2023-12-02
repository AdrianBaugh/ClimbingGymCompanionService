package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateRouteStatusRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateRouteStatusResult;
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

public class UpdateRouteStatusActivity {
    private final Logger log = LogManager.getLogger();
    private final RouteDao routeDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Update route activity.
     * @param routeDao the routeDao to access the database
     * @param metricsPublisher for metrics
     */
    @Inject
    public UpdateRouteStatusActivity(RouteDao routeDao, MetricsPublisher metricsPublisher) {
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
     * @param updateRouteStatusRequest request an object containing the route metadata
     *                              associated with it
     * @return UpdateRouteStatusResult a result object containing the API defined {@link RouteModel}
     */
    public UpdateRouteStatusResult handleRequest(final UpdateRouteStatusRequest updateRouteStatusRequest) {
        log.info("Received the Update RouteRequest {}", updateRouteStatusRequest);

        Route route = routeDao.getRouteById(updateRouteStatusRequest.getRouteId());

        String newStatus = updateRouteStatusRequest.getRouteStatus();
        route.setRouteStatus(newStatus);
        if (Objects.equals(newStatus, RouteStatus.ARCHIVED.name())) {
            route.setIsArchived(ArchivedStatus.TRUE.name());
        } else {
            route.setIsArchived(ArchivedStatus.FALSE.name());
        }

        route = routeDao.saveRoute(route);

        return UpdateRouteStatusResult.builder()
                .withRoute(new ModelConverter().toRouteModel(route))
                .build();
    }
}
