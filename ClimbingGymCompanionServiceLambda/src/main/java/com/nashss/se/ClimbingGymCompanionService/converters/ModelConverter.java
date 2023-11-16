package com.nashss.se.ClimbingGymCompanionService.converters;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

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
                .withPictureKey(route.getPictureKey())
                .withNotesList(route.getNotesList())
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
                .withRouteId(climb.getRouteId())
                .withClimbStatus(climb.getClimbStatus())
                .withDateTimeClimbed(climb.getDateTimeClimbed())
                .withThumbsUp(climb.isThumbsUp())
                .withNotes(climb.getNotes())
                .build();
    }
}
