package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetUserInfoRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetUserInfoResult;
import com.nashss.se.ClimbingGymCompanionService.converters.ModelConverter;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;
import com.nashss.se.ClimbingGymCompanionService.models.UserInfoModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetUserInfoActivity {
    private final Logger log = LogManager.getLogger();
    private final UserInfoDao userInfoDao;

    /**
     *
     * @param userInfoDao to access the userInfo table
     */
    @Inject
    public GetUserInfoActivity(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    /**
     * This method handles the incoming request by retrieving a single userInfo object from the database.
     *
     * @param getUserInfoRequest request object containing the status to look up routes for
     * @return GetRouteResult result object containing the route API-defined RouteModel
     */
    public GetUserInfoResult handleRequest(final GetUserInfoRequest getUserInfoRequest) {
        log.info("Received GetRouteRequest {}", getUserInfoRequest);

        UserInfo userInfo = userInfoDao.getUserInfoById(getUserInfoRequest.getUserId());

        UserInfoModel userInfoModel = new ModelConverter().toUserInfoModel(userInfo);

        return GetUserInfoResult.builder()
                .withUserInfoModel(userInfoModel)
                .build();
    }
}
