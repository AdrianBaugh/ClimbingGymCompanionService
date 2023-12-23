package com.nashss.se.ClimbingGymCompanionService.converters;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;
import com.nashss.se.ClimbingGymCompanionService.models.UserInfoModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    /**
     * Converts a Routes to a Route Models.
     *
     * @param route The route to convert to Route Model.
     * @return The converted Route Model.
     */
    public RouteModel toRouteModel(Route route) {
        return RouteModel.builder()
                .withRouteId(route.getRouteId())
                .withRouteStatus(route.getRouteStatus())
                .withIsArchived(route.getIsArchived())
                .withLocation(route.getLocation())
                .withColor(route.getColor())
                .withType(route.getType())
                .withDifficulty(route.getDifficulty())
                .withDateCreated(route.getDateCreated())
                .withRating(route.getRating())
                .withImageName(route.getImageName())
                .withImageKey(route.getImageKey())
                .withBetaMap(route.getBetaMap())
                .build();

    }
    /**
     * Converts a list of Routes to a list of Route Models.
     *
     * @param activeRoutes The routes to convert to Route Models.
     * @return The converted list of Route Models.
     */
    public List<RouteModel> toRouteModelList(List<Route> activeRoutes) {
        return activeRoutes.stream()
                .map(route -> toRouteModel(route))
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of Routes to a list of Route Models.
     *
     * @param climb The climb to convert to climb Models.
     * @return The converted climb Model.
     */
    public ClimbModel toClimbModel(Climb climb) {
        return ClimbModel.builder()
                .withClimbId(climb.getClimbId())
                .withUserId(climb.getUserId())
                .withType(climb.getType())
                .withRouteId(climb.getRouteId())
                .withClimbStatus(climb.getClimbStatus())
                .withDateTimeClimbed(climb.getDateTimeClimbed())
                .withWeekClimbed(climb.getWeekClimbed())
                .withThumbsUp(climb.isThumbsUp())
                .withNotes(climb.getPublicBeta())
                .build();
    }

    /**
     * Converts a list of Climbs to a list of Climb Models.
     *
     * @param climbList The climbs to convert to Climb Models.
     * @return The converted list of Climb Models.
     */
    public List<ClimbModel> toClimbModelList(List<Climb> climbList) {
        return climbList.stream()
                .map(climb -> toClimbModel(climb))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userInfo to convert to model
     * @return userInfoModel
     */
    public UserInfoModel toUserInfoModel(UserInfo userInfo) {
        return UserInfoModel.builder()
                .withUserId(userInfo.getUserId())
                .withRecentWeeklyClimbsFrequencyMap(userInfo.getRecentWeeklyClimbsFrequencyMap())
                .withDifficultyFrequencyMap(userInfo.getDifficultyFrequencyMap())
                .withWeeklyDifficultyFrequencyMap(userInfo.getWeeklyDifficultyFrequencyMap())
                .withPercentFlashedSentMap(userInfo.getPercentFlashedSentMap())
                .withTotalCompletedClimbs(userInfo.getTotalCompletedClimbs())
                .withStat6(userInfo.getStat6())
                .withStat7(userInfo.getStat7())
                .withStat8(userInfo.getStat8())
                .build();
    }
}
