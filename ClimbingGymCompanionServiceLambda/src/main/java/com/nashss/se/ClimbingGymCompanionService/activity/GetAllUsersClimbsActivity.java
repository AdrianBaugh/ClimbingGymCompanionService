package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllUsersClimbsRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllUsersClimbsResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetAllUsersClimbsActivity for the ClimbingGymCompanionService API.
 * <p>
 * This API allows the customer to get the list of all the climbs the user has.
 */
public class GetAllUsersClimbsActivity {

    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;

    /**
     * Instantiates a new GetAllUsersClimbsActivity object.
     *
     * @param climbDao climbDao to access the climbs table.
     */
    @Inject
    public GetAllUsersClimbsActivity(ClimbDao climbDao) {
        this.climbDao = climbDao;
    }

    /**
     * This method handles the incoming request by retrieving all the climbs the user has from the database.
     * <p>
     * It then returns the climbs list.
     * <p>
     * If no climbs exist, this should throw a ClimbNotFoundException.
     *
     * @param getAllUsersClimbsRequest request object containing the status to look up routes for.
     * @return GetAllUsersClimbsResult result object containing the route's list of API-defined RouteModels.
     */
    public GetAllUsersClimbsResult handleRequest(final GetAllUsersClimbsRequest getAllUsersClimbsRequest) {
        log.info("Received GetAllUsersClimbsRequest {}", getAllUsersClimbsRequest);

        List<Climb> climbs = climbDao.getAllUsersClimbs(getAllUsersClimbsRequest.getUserId());
        List<ClimbModel> climbModels = new ModelConverter().toClimbModelList(climbs);

        return GetAllUsersClimbsResult.builder()
                .withClimbList(climbModels)
                .build();
    }
}
