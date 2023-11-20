package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetClimbResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetClimbActivity for the ClimbingGymCompanionService API.
 * This API allows the customer to get info on a single climb from the gym.
 */
public class GetClimbActivity {
    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;

    /**
     * Instantiates a new GetClimbActivity object.
     * @param climbDao to access the climbs table;
     */
    @Inject
    public GetClimbActivity(ClimbDao climbDao) {
        this.climbDao = climbDao;
    }

    /**
     * This method handles the incoming request by retrieving a single route object for the gym from the database.
     * It then returns the routes list.
     * If no route does not exist, this should throw a RouteNotFoundException.
     *
     * @param getClimbRequest request object containing the status to look up routes for
     * @return GetRouteResult result object containing the route API-defined RouteModel
     */
    public GetClimbResult handleRequest(final GetClimbRequest getClimbRequest) {
        log.info("Received GetClimbRequest {}", getClimbRequest);

        Climb climb = climbDao.getClimbById(getClimbRequest.getClimbId(), getClimbRequest.getUserId());
        ClimbModel climbModel = new ModelConverter().toClimbModel(climb);

        return GetClimbResult.builder()
                .withClimb(climbModel)
                .build();
    }
}
