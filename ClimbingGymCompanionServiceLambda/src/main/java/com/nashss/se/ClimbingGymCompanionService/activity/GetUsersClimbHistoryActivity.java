package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetUsersClimbHistoryRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetUsersClimbHistoryResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetUsersClimbHistoryActivity for the ClimbingGymCompanionService API.
 * <p>
 * This API allows the customer to get the list of all the climbs the user has.
 */
public class GetUsersClimbHistoryActivity {

    private final Logger log = LogManager.getLogger();
    private final ClimbDao climbDao;

    /**
     * Instantiates a new GetUsersClimbHistoryActivity object.
     *
     * @param climbDao climbDao to access the climbs table.
     */
    @Inject
    public GetUsersClimbHistoryActivity(ClimbDao climbDao) {
        this.climbDao = climbDao;
    }

    /**
     * This method handles the incoming request by retrieving all the climbs the user has from the database.
     * <p>
     * It then returns the climbs list.
     * <p>
     * If no climbs exist, this should throw a ClimbNotFoundException.
     *
     * @param getUsersClimbHistoryRequest request object containing the status to look up routes for.
     * @return GetUsersClimbHistoryResult result object containing the route's list of API-defined RouteModels.
     */
    public GetUsersClimbHistoryResult handleRequest(final GetUsersClimbHistoryRequest getUsersClimbHistoryRequest) {
        log.info("Received GetUsersClimbHistoryRequest {}", getUsersClimbHistoryRequest);

        List<Climb> climbs = climbDao.getAllUsersClimbs(getUsersClimbHistoryRequest.getUserId());

        List<Climb> sorted = new ArrayList<>(climbs);
        Collections.sort(sorted, Comparator.comparing(Climb::getDateTimeClimbed).reversed());

        List<ClimbModel> climbModels = new ModelConverter().toClimbModelList(sorted);

        return GetUsersClimbHistoryResult.builder()
                .withClimbList(climbModels)
                .build();
    }
}
