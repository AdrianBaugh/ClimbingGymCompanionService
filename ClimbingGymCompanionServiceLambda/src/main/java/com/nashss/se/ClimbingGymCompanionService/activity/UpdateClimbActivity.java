package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
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
            climb.setThumbsUp(thumbsUp);
        }
        if (notes != null) {
            updateRouteNotes(notes, routeId);
            climb.setNotes(notes);
        }

        climbDao.saveClimb(climb);
        ClimbModel climbModel = new ModelConverter().toClimbModel(climb);

        return UpdateClimbResult.builder()
                .withClimb(climbModel)
                .build();
    }

    /**
     * Helper to update the notes list for particular route that was climbed.
     * @param newNote the newNote
     * @param routeId the route to add the newNote to.
     */
    private void updateRouteNotes(String newNote, String routeId) {
        Route route = routeDao.getRouteById(routeId);

        List<String> notes = route.getNotesList();
        notes.add(newNote);

        route.setNotesList(notes);

        routeDao.saveRoute(route);
    }
}
