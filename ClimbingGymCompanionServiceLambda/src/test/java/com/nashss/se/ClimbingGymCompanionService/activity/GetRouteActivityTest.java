package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetRouteResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.RouteColor;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetRouteActivityTest {

    @Mock
    private RouteDao routeDao;
    private GetRouteActivity getRouteActivity;

    @BeforeEach
    void setUp(){
        openMocks(this);
        getRouteActivity = new GetRouteActivity(routeDao);
    }

    @Test
    public void handleRequest_withValidRouteID_returnsRouteModel(){
        // GIVEN
        String expectedRouteId = "validRouteID";
        Route route = new Route();
        route.setRouteId(expectedRouteId);
        route.setColor(RouteColor.GREEN.name());

        when(routeDao.getRouteById(expectedRouteId)).thenReturn(route);

        GetRouteRequest request = GetRouteRequest.builder()
                .withRouteId(expectedRouteId)
                .build();



        //WHEN
        GetRouteResult result= getRouteActivity.handleRequest(request);

        //THEN
        assertNotNull(result);
        RouteModel routeModel = result.getRoute();
        assertNotNull(routeModel);
        assertEquals(expectedRouteId, routeModel.getRouteId());
        assertEquals("GREEN", routeModel.getColor());
    }
}
