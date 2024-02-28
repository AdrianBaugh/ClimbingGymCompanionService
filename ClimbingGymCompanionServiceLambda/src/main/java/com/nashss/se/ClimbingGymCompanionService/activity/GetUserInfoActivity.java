package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetUserInfoRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetUserInfoResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;
import com.nashss.se.ClimbingGymCompanionService.models.UserInfoModel;
import com.nashss.se.ClimbingGymCompanionService.utils.UpdateUserInfoUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

public class GetUserInfoActivity {
    private final Logger log = LogManager.getLogger();
    private final UserInfoDao userInfoDao;
    private final ClimbDao climbDao;
    private final RouteDao routeDao;

    /**
     *
     * @param userInfoDao to access the userInfo table
     */
    @Inject
    public GetUserInfoActivity(UserInfoDao userInfoDao, ClimbDao climbDao, RouteDao routeDao) {
        this.userInfoDao = userInfoDao;
        this.climbDao = climbDao;
        this.routeDao = routeDao;
    }

    /**
     * This method handles the incoming request by retrieving a single userInfo object from the database.
     *
     * @param getUserInfoRequest request object containing the status to look up routes for
     * @return GetRouteResult result object containing the route API-defined RouteModel
     */
    public GetUserInfoResult handleRequest(final GetUserInfoRequest getUserInfoRequest) {
        log.info("Received GetRouteRequest {}", getUserInfoRequest);

        UserInfo userInfo;
        try {
            userInfo = userInfoDao.getUserInfoById(getUserInfoRequest.getUserId());
        } catch (RuntimeException e) {
            updateRequest(getUserInfoRequest);
            userInfo = userInfoDao.getUserInfoById(getUserInfoRequest.getUserId());
        }

        UserInfoModel userInfoModel = new ModelConverter().toUserInfoModel(userInfo);

        return GetUserInfoResult.builder()
                .withUserInfoModel(userInfoModel)
                .build();
    }


    private void updateRequest(GetUserInfoRequest request) {
        List<Climb> climbs = climbDao.getAllUsersClimbs(request.getUserId());

        for (Climb c : climbs) {
            UpdateUserInfoUtils.updateUserInfo(userInfoDao, routeDao, c);
        }
    }

}
