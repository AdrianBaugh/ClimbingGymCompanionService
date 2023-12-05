package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class    CreateClimbActivityTest {

    @Mock
    private ClimbDao climbDao;
    @Mock
    private RouteDao routeDao;
    private CreateClimbActivity createClimbActivity;

    private Climb otherClimb;
    private String routeId = "otherId";
    private String notes = "these are the notes";
    private Boolean thumbsUp = true;
    private List<String> notesList = new ArrayList<>();
    private Route route = new Route();



    @BeforeEach
    void setUp() {
        openMocks(this);
        createClimbActivity = new CreateClimbActivity(climbDao, routeDao);

        this.otherClimb = new Climb();
        otherClimb.setRouteId(routeId);
        otherClimb.setNotes(notes);
        otherClimb.setThumbsUp(thumbsUp);

        this.route  = new Route();
        route.setNotesList(notesList);
    }

    @Test
    public void handleRequest_withValidFields_savesClimb() {
        // GIVEN
        Climb climb = new Climb();

        String routeId = "id";

        climb.setRouteId(routeId);

        when(climbDao.saveClimb(any(Climb.class))).thenReturn(climb);


        CreateClimbRequest request = CreateClimbRequest.builder()
                .withRouteId(routeId)
                .build();

        // WHEN
        CreateClimbResult result = createClimbActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        ClimbModel climbModel = result.getClimb();
        assertNotNull(climbModel);
        assertEquals(routeId, climbModel.getRouteId());
    }

    @Test
    public void handleRequest_withNotes_savesClimbAndUpdatesRouteNotes() {
        // GIVEN
        Climb climb = new Climb();

        String routeId = "id";
        String note = "these are the notes";

        climb.setRouteId(routeId);
        climb.setNotes(note);

        Route route1 = new Route();
        route1.setNotesList(notesList);

        when(routeDao.getRouteById(any(String.class))).thenReturn(route1);
        when(routeDao.saveRoute(route1)).thenReturn(route1);
        when(climbDao.saveClimb(any(Climb.class))).thenReturn(eq(climb));

        CreateClimbRequest request = CreateClimbRequest.builder()
                .withRouteId(routeId)
                .withNotes(note)
                .build();

        // WHEN
        CreateClimbResult result = createClimbActivity.handleRequest(request);

        // THEN
        assertNotNull(result);
        ClimbModel climbModel = result.getClimb();
        assertNotNull(climbModel);
        assertEquals(routeId, climbModel.getRouteId());
        verify(routeDao).getRouteById(any(String.class));
        verify(routeDao).saveRoute(any(Route.class));
    }

    @Test
    public void handleRequest_withRating_savesClimbAndUpdatesRouteRating() {
        // GIVEN
        Climb climb = new Climb();

        String routeId = "id";
        Boolean thumbsUp = true;

        climb.setRouteId(routeId);
        climb.setThumbsUp(thumbsUp);

        List<Climb> climbsList = new ArrayList<>();
        climbsList.add(otherClimb);

        when(climbDao.getAllClimbsByRouteId(routeId)).thenReturn(climbsList);
        when(routeDao.getRouteById(any(String.class))).thenReturn(new Route());

        when(climbDao.saveClimb(any(Climb.class))).thenReturn(eq(climb));

        CreateClimbRequest request = CreateClimbRequest.builder()
                .withRouteId(routeId)
                .withThumbsUp(thumbsUp)
                .build();

        // WHEN
        CreateClimbResult result = createClimbActivity.handleRequest(request);

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
