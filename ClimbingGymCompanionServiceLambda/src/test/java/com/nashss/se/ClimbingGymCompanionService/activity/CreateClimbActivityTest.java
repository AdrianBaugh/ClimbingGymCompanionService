package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class    CreateClimbActivityTest {

    @Mock
    private ClimbDao climbDao;
    @Mock
    private RouteDao routeDao;
    private CreateClimbActivity createClimbActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createClimbActivity = new CreateClimbActivity(climbDao, routeDao);
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
}
