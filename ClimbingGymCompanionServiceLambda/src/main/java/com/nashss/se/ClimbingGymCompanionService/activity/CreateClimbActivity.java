package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;
import com.nashss.se.ClimbingGymCompanionService.utils.DateTimeUtils;
import com.nashss.se.ClimbingGymCompanionService.utils.IdUtils;
import com.nashss.se.ClimbingGymCompanionService.utils.UpdateRouteUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.ZonedDateTime;
import javax.inject.Inject;

public class CreateClimbActivity {
    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;
    private final RouteDao routeDao;

    /**
     * Instantiates a new CreateClimbActivity object.
     *
     * @param climbDao to access the climbs table.
     * @param routeDao to access routes table.
     */
    @Inject
    public CreateClimbActivity(ClimbDao climbDao, RouteDao routeDao) {
        this.climbDao = climbDao;
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request by persisting a new climb
     * with the provided climb metadata from the request.
     * <p>
     * It then returns the newly created climb with a generated climbId.
     * <p>
     *
     * @param createClimbRequest request an object containing the climb metadata
     *                              associated with it
     * @return CreateClimbResult a result object containing the API defined {@link ClimbModel}
     */
    public CreateClimbResult handleRequest(final CreateClimbRequest createClimbRequest) {
        log.info("received createClimbRequest {}", createClimbRequest);

        ZonedDateTime dateTime = DateTimeUtils.getDateTime();
        String routeId = createClimbRequest.getRouteId();
        String notes = createClimbRequest.getPublicBeta();
        Boolean rating = createClimbRequest.getThumbsUp();

        Climb newClimb = new Climb();
        newClimb.setClimbId(IdUtils.generateClimbId(dateTime));
        newClimb.setUserId(createClimbRequest.getUserId());
        newClimb.setType(createClimbRequest.getType());
        newClimb.setRouteId(routeId);
        newClimb.setClimbStatus(createClimbRequest.getClimbStatus());
        newClimb.setDateTimeClimbed(dateTime);

        if (rating != null) {
            UpdateRouteUtils.updateRouteRating(climbDao, routeDao, rating, routeId);
        }
        newClimb.setThumbsUp(rating);

        if (notes != null) {
            UpdateRouteUtils.updateRouteNotes(routeDao, notes, routeId, createClimbRequest.getUserName());
        }
        newClimb.setPublicBeta(notes);

        climbDao.saveClimb(newClimb);

        ClimbModel climbModel = new ModelConverter().toClimbModel(newClimb);

        return CreateClimbResult.builder()
                .withClimb(climbModel)
                .build();
    }
}
