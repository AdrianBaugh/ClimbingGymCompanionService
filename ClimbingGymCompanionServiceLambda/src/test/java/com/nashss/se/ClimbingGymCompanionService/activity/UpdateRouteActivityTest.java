package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateRouteResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.RouteStatus;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateRouteActivityTest {
    @Mock
    private RouteDao routeDao;
    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateRouteActivity updateRouteActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateRouteActivity = new UpdateRouteActivity(routeDao, metricsPublisher);
    }

    @Test
    public void handleRequest_goodRequest_updatesRouteStatus() {

        // GIVEN
        String routeId = "expectedId";

        UpdateRouteRequest request = UpdateRouteRequest.builder()
                .withRouteId(routeId)
                .withRouteStatus(RouteStatus.ARCHIVED.name())
                .build();

        Route orginalRoute = new Route();
        orginalRoute.setRouteId(routeId);
        orginalRoute.setRouteStatus(RouteStatus.ACTIVE.name());

        when(routeDao.getRouteById(routeId)).thenReturn(orginalRoute);
        when(routeDao.saveRoute(orginalRoute)).thenReturn(orginalRoute);

        // WHEN
        UpdateRouteResult result = updateRouteActivity.handleRequest(request);

        // THEN
        assertEquals(RouteStatus.ARCHIVED.name(), result.getRoute().getRouteStatus());
        assertEquals(routeId, result.getRoute().getRouteId());
    }
}
