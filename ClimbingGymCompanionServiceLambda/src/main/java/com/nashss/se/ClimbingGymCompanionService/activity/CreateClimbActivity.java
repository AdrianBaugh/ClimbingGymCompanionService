package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateClimbResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;
import com.nashss.se.ClimbingGymCompanionService.utils.IdUtils;

import javax.inject.Inject;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateClimbActivity {
    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;

    /**
     * Instantiates a new CreateClimbActivity object.
     *
     * @param climbDao to access the climbs table.
     */
    @Inject
    public CreateClimbActivity(ClimbDao climbDao) {
        this.climbDao = climbDao;
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

        LocalDateTime dateTime = LocalDateTime.now();

        Climb newClimb = new Climb();
        newClimb.setClimbId(IdUtils.generateClimbId(dateTime));
        newClimb.setUserId(createClimbRequest.getUserId());
        newClimb.setRouteId(createClimbRequest.getRouteId());
        newClimb.setClimbStatus(createClimbRequest.getClimbStatus());
        newClimb.setDateTimeClimbed(dateTime);
        newClimb.setThumbsUp(createClimbRequest.getThumbsUp());
        newClimb.setNotes(createClimbRequest.getNotes());

        climbDao.saveClimb(newClimb);

        ClimbModel climbModel = new ModelConverter().toClimbModel(newClimb);

        return CreateClimbResult.builder()
                .withClimb(climbModel)
                .build();
    }
}
