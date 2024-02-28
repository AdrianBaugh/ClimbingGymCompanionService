//package com.nashss.se.ClimbingGymCompanionService.activity;
//
//import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateClimbRequest;
//import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateClimbResult;
//import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
//import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
//import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
//import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
//import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
//import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;
//import com.nashss.se.ClimbingGymCompanionService.utils.UpdateUserInfoUtils;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.openMocks;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(UpdateUserInfoUtils.class)
//public class CreateClimbActivityTest {
//
//    @Mock
//    private ClimbDao climbDao;
//    @Mock
//    private RouteDao routeDao;
//    @Mock
//    private UserInfoDao userInfoDao;
//    private CreateClimbActivity createClimbActivity;
//
//    private Climb otherClimb;
//    private String routeId = "otherId";
//    private String notes = "these are the notes";
//    private Boolean thumbsUp = true;
//    private Map<String, String> betaMap = new HashMap<>();
//    private Route route = new Route();
//
///*
//!!!!!!!!!!!!!!!!!!!!!! WAIT !!!!!!!!!!!!!!!!!!!!!!!!!!!
//these tests need to be refactored to include mocking of the static methods from the Update User Info Class
// */
//
//    @BeforeEach
//    void setUp() {
//        openMocks(this);
//        createClimbActivity = new CreateClimbActivity(climbDao, routeDao, userInfoDao);
//        this.otherClimb = new Climb();
//        otherClimb.setRouteId(routeId);
//        otherClimb.setPublicBeta(notes);
//        otherClimb.setThumbsUp(thumbsUp);
//
//        this.route  = new Route();
//        route.setBetaMap(betaMap);
//    }
//
//    @Test
//    public void handleRequest_withValidFields_savesClimb() throws Exception {
//        // GIVEN
//        Climb climb = new Climb();
//
//        String routeId = "id";
//
//        climb.setRouteId(routeId);
//
//        when(climbDao.saveClimb(any(Climb.class))).thenReturn(climb);
//
//        PowerMockito.mockStatic(UpdateUserInfoUtils.class);
//        PowerMockito.doNothing().when(UpdateUserInfoUtils.class, "updateUserInfo", userInfoDao, routeDao, climb);
//
//
//        CreateClimbRequest request = CreateClimbRequest.builder()
//                .withRouteId(routeId)
//                .build();
//
//        // WHEN
//        CreateClimbResult result = createClimbActivity.handleRequest(request);
//
//        // THEN
//        assertNotNull(result);
//        ClimbModel climbModel = result.getClimb();
//        assertNotNull(climbModel);
//        assertEquals(routeId, climbModel.getRouteId());
//        PowerMockito.verifyStatic(UpdateUserInfoUtils.class);
//    }
//
//    @Test
//    public void handleRequest_withNotes_savesClimbAndUpdatesRouteNotes() {
//        // GIVEN
//        Climb climb = new Climb();
//
//        String routeId = "id";
//        String publicBeta = "these are the notes";
//
//        climb.setRouteId(routeId);
//        climb.setPublicBeta(publicBeta);
//
//        Route route1 = new Route();
//        route1.setBetaMap(betaMap);
//
//        when(routeDao.getRouteById(any(String.class))).thenReturn(route1);
//        when(routeDao.saveRoute(route1)).thenReturn(route1);
//        when(climbDao.saveClimb(any(Climb.class))).thenReturn(eq(climb));
//
//        CreateClimbRequest request = CreateClimbRequest.builder()
//                .withRouteId(routeId)
//                .withPublicBeta(publicBeta)
//                .build();
//
//        // WHEN
//        CreateClimbResult result = createClimbActivity.handleRequest(request);
//
//        // THEN
//        assertNotNull(result);
//        ClimbModel climbModel = result.getClimb();
//        assertNotNull(climbModel);
//        assertEquals(routeId, climbModel.getRouteId());
//        verify(routeDao).getRouteById(any(String.class));
//        verify(routeDao).saveRoute(any(Route.class));
//    }
//
//    @Test
//    public void handleRequest_withRating_savesClimbAndUpdatesRouteRating() {
//        // GIVEN
//        Climb climb = new Climb();
//
//        String routeId = "id";
//        Boolean thumbsUp = true;
//
//        climb.setRouteId(routeId);
//        climb.setThumbsUp(thumbsUp);
//
//        List<Climb> climbsList = new ArrayList<>();
//        climbsList.add(otherClimb);
//
//        when(climbDao.getAllClimbsByRouteId(routeId)).thenReturn(climbsList);
//        when(routeDao.getRouteById(any(String.class))).thenReturn(new Route());
//
//        when(climbDao.saveClimb(any(Climb.class))).thenReturn(eq(climb));
//
//        CreateClimbRequest request = CreateClimbRequest.builder()
//                .withRouteId(routeId)
//                .withThumbsUp(thumbsUp)
//                .build();
//
//        // WHEN
//        CreateClimbResult result = createClimbActivity.handleRequest(request);
//
//        // THEN
//        assertNotNull(result);
//        ClimbModel climbModel = result.getClimb();
//        assertNotNull(climbModel);
//        assertEquals(routeId, climbModel.getRouteId());
//        verify(routeDao).getRouteById(any(String.class));
//        verify(routeDao).saveRoute(any(Route.class));
//        verify(climbDao).getAllClimbsByRouteId(any(String.class));
//    }
//}
