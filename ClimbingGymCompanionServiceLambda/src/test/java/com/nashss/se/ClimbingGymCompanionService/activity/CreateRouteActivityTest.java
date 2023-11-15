package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateRouteResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateRouteActivityTest {
    @Mock
    private RouteDao routeDao;
    private CreateRouteActivity createRouteActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createRouteActivity = new CreateRouteActivity(routeDao);
    }

    @Test
    public void handleRequest_withValidFields_savesRoute() {
        // GIVEN
        Route route = new Route();
        route.setColor("COLOR");
        route.setType("TYPE");

        when(routeDao.saveRoute(any(Route.class))).thenReturn(route);
        CreateRouteRequest request = CreateRouteRequest.builder()
                .withColor("COLOR")
                .withType("TYPE")
                .build();

        // WHEN
        CreateRouteResult result = createRouteActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        RouteModel routeModel = result.getRoute();
        assertNotNull(routeModel);
        assertEquals("TYPE", routeModel.getType());
        assertEquals("COLOR", routeModel.getColor());
    }
}
