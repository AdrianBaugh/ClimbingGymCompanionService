package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateClimbActivityTest {
    @Mock
    private ClimbDao climbDao;
    @Mock
    private RouteDao routeDao;
    private UpdateClimbActivity activity;
    private Map<String, String> betaMap = new HashMap<>();
    String type = "type";
    String userId = "userId";
    String climbId = "climbId";
    String routeId = "routeId";
    Climb climb;
    @BeforeEach
    void setUp() {
        openMocks(this);
        activity = new UpdateClimbActivity(climbDao, routeDao);
        this.climb = new Climb();
        climb.setType(type);
        climb.setClimbId(climbId);
        climb.setUserId(userId);
        climb.setRouteId(routeId);
    }

    @Test
    public void handleRequest_withValidFields_updatesClimb() {
        // GIVEN

        when(climbDao.getClimbById(climbId, userId)).thenReturn(climb);
        when(climbDao.saveClimb(any(Climb.class))).thenReturn(climb);

        UpdateClimbRequest request = UpdateClimbRequest.builder()
                .withType(type)
                .withUserId(userId)
                .withClimbId(climbId)
                .build();

        // WHEN
        UpdateClimbResult result = activity.handleRequest(request);

        // THEN
        assertNotNull(result);
        ClimbModel climbModel = result.getClimb();
        assertNotNull(climbModel);
        assertEquals(type, climbModel.getType());
    }

    @Test
    public void handleRequest_withNotes_updatesClimbAndUpdatesRouteNotes() {
        // GIVEN
        Climb climbWithNotes = new Climb();

        String userId = "userId";
        String climbId = "climbId";
        String routeId = "routeId";
        String type = "type";
        String note = "these are the notes";

        climbWithNotes.setUserId(userId);
        climbWithNotes.setClimbId(climbId);
        climbWithNotes.setRouteId(routeId);
        climbWithNotes.setType(type);
        climbWithNotes.setPublicBeta(note);

        Route route1 = new Route();
        route1.setBetaMap(betaMap);

        when(climbDao.getClimbById(any(String.class), any(String.class))).thenReturn(climbWithNotes);

        when(routeDao.getRouteById(any(String.class))).thenReturn(route1);
        when(routeDao.saveRoute(route1)).thenReturn(route1);
        when(climbDao.saveClimb(climbWithNotes)).thenReturn(climbWithNotes);

        UpdateClimbRequest request = UpdateClimbRequest.builder()
                .withPublicBeta(note)
                .withClimbId(climbId)
                .withUserId(userId)
                .withType(type)
                .build();

        // WHEN
        UpdateClimbResult result = activity.handleRequest(request);

        // THEN
        assertNotNull(result);
        ClimbModel climbModel = result.getClimb();
        assertNotNull(climbModel);
        assertEquals(routeId, climbModel.getRouteId());
        verify(routeDao).getRouteById(any(String.class));
        verify(routeDao).saveRoute(any(Route.class));
    }

    @Test
    public void handleRequest_withRating_updatesClimbAndUpdatesRouteRating() {
        // GIVEN
        Climb climbWithRating = new Climb();

        String userId = "userId";
        String climbId = "climbId";
        String routeId = "routeId";
        Boolean thumbsUp = true;

        climbWithRating.setThumbsUp(thumbsUp);
        climbWithRating.setUserId(userId);
        climbWithRating.setClimbId(climbId);
        climbWithRating.setRouteId(routeId);

        List<Climb> climbsList = new ArrayList<>();
        climbsList.add(this.climb);

        when(climbDao.getClimbById(any(String.class), any(String.class))).thenReturn(climbWithRating);

        when(climbDao.getAllClimbsByRouteId(any(String.class))).thenReturn(climbsList);
        when(routeDao.getRouteById(any(String.class))).thenReturn(new Route());

        when(climbDao.saveClimb(any(Climb.class))).thenReturn(climbWithRating);

        UpdateClimbRequest request = UpdateClimbRequest.builder()
                .withClimbId(climbId)
                .withUserId(userId)
                .withThumbsUp(thumbsUp)
                .build();

        // WHEN
        UpdateClimbResult result = activity.handleRequest(request);

        // THEN
        assertNotNull(result);
        ClimbModel climbModel = result.getClimb();
        assertNotNull(climbModel);
        assertEquals(routeId, climbModel.getRouteId());
        verify(routeDao).getRouteById(any(String.class));
        verify(routeDao).saveRoute(any(Route.class));
        verify(climbDao).getAllClimbsByRouteId(any(String.class));
    }
}