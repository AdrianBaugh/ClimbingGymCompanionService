package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;
import com.nashss.se.ClimbingGymCompanionService.utils.UpdateRouteUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateClimbActivity {
    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;
    private final RouteDao routeDao;

    /**
     * Update climb activity.
     * @param climbDao the climbDao to access the database
     * @param routeDao for access to routes table
     */
    @Inject
    public UpdateClimbActivity(ClimbDao climbDao, RouteDao routeDao) {
        this.climbDao = climbDao;
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request to update a climb's info
     * with the provided climb metadata from the request.
     * <p>
     * It then returns the updated climb with a constructed routeId.
     * <p>
     *
     * @param updateClimbRequest request an object containing the climb metadata
     *                              associated with it
     * @return UpdateClimbResult a result object containing the API defined {@link RouteModel}
     */
    public UpdateClimbResult handleRequest(final UpdateClimbRequest updateClimbRequest) {
        log.info("Received the Update Climb Request {}", updateClimbRequest);

        Climb climb = climbDao.getClimbById(updateClimbRequest.getClimbId(), updateClimbRequest.getUserId());
        String routeId = climb.getRouteId();

        String type = updateClimbRequest.getType();
        String climbStatus = updateClimbRequest.getClimbStatus();
        Boolean thumbsUp = updateClimbRequest.getThumbsUp();
        String notes = updateClimbRequest.getNotes();

        if (type != null) {
            climb.setType(type);
        }
        if (climbStatus != null) {
            climb.setClimbStatus(climbStatus);
        }
        if (thumbsUp != null) {
            UpdateRouteUtils.updateRouteRating(climbDao, routeDao, thumbsUp, routeId);
            climb.setThumbsUp(thumbsUp);
        }
        if (notes != null) {
            UpdateRouteUtils.updateRouteNotes(routeDao, notes, routeId);
            climb.setNotes(notes);
        }

        climbDao.saveClimb(climb);
        ClimbModel climbModel = new ModelConverter().toClimbModel(climb);

        return UpdateClimbResult.builder()
                .withClimb(climbModel)
                .build();
    }
}
