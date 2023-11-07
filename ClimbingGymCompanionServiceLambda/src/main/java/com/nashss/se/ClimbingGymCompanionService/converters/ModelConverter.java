package com.nashss.se.ClimbingGymCompanionService.converters;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    public RouteModel toRouteModel(Route route) {
        return RouteModel.builder()
                .withRouteId(route.getRouteId())
                .withRouteStatus(route.getRouteStatus())
                .withLocation(route.getLocation())
                .withColor(route.getColor())
                .withType(route.getType())
                .withDifficulty(route.getDifficulty())
                .withDateCreated(route.getDateCreated())
                .withRating(route.getRating())
                .withPictureKey(route.getPictureKey())
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
}
