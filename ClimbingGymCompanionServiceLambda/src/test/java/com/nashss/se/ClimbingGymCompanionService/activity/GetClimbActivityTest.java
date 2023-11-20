package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetClimbResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetClimbActivityTest {
    @Mock
    private ClimbDao climbDao;
    private GetClimbActivity getClimbActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        getClimbActivity = new GetClimbActivity(climbDao);
    }

    @Test
    public void handleRequest_withIDs_returnsClimb(){
        // GIVEN
        String expectedClimbId = "validClimbID";
        String expectedUserId = "validUserID";
        Climb climb = new Climb();
        climb.setClimbId(expectedClimbId);
        climb.setUserId(expectedUserId);

        when(climbDao.getClimbById(expectedClimbId, expectedUserId)).thenReturn(climb);

        GetClimbRequest request = GetClimbRequest.builder()
                .withClimbId(expectedClimbId)
                .withUserId(expectedUserId)
                .build();

        //WHEN
        GetClimbResult result= getClimbActivity.handleRequest(request);

        //THEN
        assertNotNull(result);
        ClimbModel climbModel = result.getClimb();
        assertNotNull(climbModel);
        assertEquals(expectedClimbId, climbModel.getClimbId());
        assertEquals(expectedUserId, climbModel.getUserId());
    }
}
