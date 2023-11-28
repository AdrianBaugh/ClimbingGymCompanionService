package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.DeleteClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.DeleteClimbResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteClimbActivityTest {
    @InjectMocks
    private DeleteClimbActivity activity;
    @Mock
    private ClimbDao climbDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_AttempsToDeleteClimb_isSuccessful() {
        // GIVEN
        String userId = "userId";
        String climbId = "climbId";

        DeleteClimbRequest request = DeleteClimbRequest.builder()
                .withUserId(userId)
                .withClimbId(climbId)
                .build();

        when(climbDao.deleteClimb(userId,climbId)).thenReturn(true);

        // WHEN
        DeleteClimbResult result = activity.handleRequest(request);

        // THEN
        verify(climbDao).deleteClimb(userId, climbId);
        assertEquals(climbId, result.getClimbId());
        assertEquals(userId, result.getUserId());
    }
}