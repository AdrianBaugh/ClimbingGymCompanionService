package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetUsersClimbHistoryRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetUsersClimbHistoryResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetUsersClimbHistoryActivityTest {
    @Mock
    private ClimbDao climbDao;
    private GetUsersClimbHistoryActivity getUsersClimbHistoryActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        getUsersClimbHistoryActivity = new GetUsersClimbHistoryActivity(climbDao);
    }

    @Test
    public void handleRequest_withIDs_returnsClimbs(){
        // GIVEN
        String expectedClimbId = "validClimbID";
        String expectedUserId = "validUserID";
        Climb climb = new Climb();
        climb.setClimbId(expectedClimbId);
        climb.setUserId(expectedUserId);

        String expectedClimbId2 = "validClimbID2";

        Climb climb2 = new Climb();
        climb2.setClimbId(expectedClimbId2);
        climb2.setUserId(expectedUserId);

        List<Climb> climbs = List.of(climb, climb2);

        when(climbDao.getAllUsersClimbs(expectedUserId)).thenReturn(climbs);

        GetUsersClimbHistoryRequest request = GetUsersClimbHistoryRequest.builder()
                .withUserId(expectedUserId)
                .build();

        //WHEN
        GetUsersClimbHistoryResult result = getUsersClimbHistoryActivity.handleRequest(request);

        //THEN
        assertNotNull(result);
        List<ClimbModel> climbList = result.getClimbList();
        assertNotNull(climbList);
        assertEquals(2, climbList.size());
    }

}