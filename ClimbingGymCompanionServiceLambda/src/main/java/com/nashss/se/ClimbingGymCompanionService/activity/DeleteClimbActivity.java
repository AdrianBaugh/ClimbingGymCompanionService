package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.DeleteClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.DeleteClimbResult;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteClimbActivity {
    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;

    /**
     *  Instantiates a deleteClimbActivity.
     * @param climbDao to access climbs table to delete the climb from
     */
    @Inject
    public DeleteClimbActivity(ClimbDao climbDao) {
        this.climbDao = climbDao;
    }

    /**
     *
     * @param deleteClimbRequest metadata
     * @return DeleteClimbResult
     */
    public DeleteClimbResult handleRequest(final DeleteClimbRequest deleteClimbRequest) {
        log.info("received delete climb request {}", deleteClimbRequest);

        String userId = deleteClimbRequest.getUserId();
        String climbId = deleteClimbRequest.getClimbId();

        Boolean result = climbDao.deleteClimb(userId, climbId);

        return new DeleteClimbResult.Builder()
                .withUserId(userId)
                .withClimbId(climbId)
                .build();
    }
}
