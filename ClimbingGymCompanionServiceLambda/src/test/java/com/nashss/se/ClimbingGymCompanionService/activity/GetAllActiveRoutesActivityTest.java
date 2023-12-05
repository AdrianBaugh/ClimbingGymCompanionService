package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllActiveRoutesRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllActiveRoutesResult;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetRouteResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.RouteColor;
import com.nashss.se.ClimbingGymCompanionService.enums.RouteStatus;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetAllActiveRoutesActivityTest {

    @Mock
    private RouteDao routeDao;
    private GetAllActiveRoutesActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new GetAllActiveRoutesActivity(routeDao);

    }

    @Test
    public void handleRequest_withValidStatus_returnsRouteList(){
        // GIVEN

        String activeRouteStatus = RouteStatus.ACTIVE.name();
        Route route = new Route();
        String expectedRouteId = "validRouteID";
        String location = "location1";
        route.setRouteId(expectedRouteId);
        route.setRouteStatus(activeRouteStatus);
        route.setLocation(location);

        Route route2 = new Route();
        String expectedRouteId2 = "validRouteID2";
        String location2 = "location2";
        route2.setRouteId(expectedRouteId2);
        route2.setRouteStatus(activeRouteStatus);
        route2.setLocation(location2);

        List<Route> routeList = new ArrayList<>();
        routeList.add(route);
        routeList.add(route2);

        when(routeDao.getAllActiveRoutes(activeRouteStatus)).thenReturn(routeList);

        GetAllActiveRoutesRequest request = GetAllActiveRoutesRequest.builder()
                .withIsArchived(activeRouteStatus)
                .build();

        //WHEN
        GetAllActiveRoutesResult result = activity.handleRequest(request);

        //THEN
        assertNotNull(result);
        List<RouteModel> routeModelList = result.getRouteList();
        assertNotNull(routeModelList);
        assertEquals(expectedRouteId, routeModelList.get(0).getRouteId());
        assertEquals(location, routeModelList.get(0).getLocation());
    }
}