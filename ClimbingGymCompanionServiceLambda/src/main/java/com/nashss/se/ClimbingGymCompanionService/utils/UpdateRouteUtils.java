package com.nashss.se.ClimbingGymCompanionService.utils;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;

import java.util.List;
import java.util.Map;

public class UpdateRouteUtils {

    private UpdateRouteUtils() {
    }
    /**
     * Helper to update the notes list for particular route that was climbed.
     * @param newBeta the newNote
     * @param routeId the route to add the newNote to.
     * @param routeDao route table data access object
     * @param userName users name
     */
    public static void updateRouteNotes(RouteDao routeDao, String newBeta, String routeId, String userName) {
        Route route = routeDao.getRouteById(routeId);

        Map<String, String> notes = route.getBetaMap();
        notes.put(newBeta, userName);

        route.setBetaMap(notes);

        routeDao.saveRoute(route);
    }

    /**
     * Helper to update the rating for a particular route that was climbed.
     * only climbs where the user recorded a thumbs up or thumbs down are counted
     * non-rated climbs are ignored
     * @param routeId the route to update the rating to.
     * @param currRating the current rating to update with
     * @param routeDao route table data access object
     * @param climbDao climb table data access object
     */
    public static void updateRouteRating(ClimbDao climbDao, RouteDao routeDao, Boolean currRating, String routeId) {
        List<Climb> climbs = climbDao.getAllClimbsByRouteId(routeId);

        int thumbsUpCount = 0;
        double totalCount = 0;

        if (currRating) {
            thumbsUpCount++;
        }
        totalCount++;

        for (Climb climb: climbs) {
            Boolean rating = climb.isThumbsUp();
            if (rating != null && rating) {
                thumbsUpCount++;
                totalCount++;
            } else if (rating != null && !rating) {
                totalCount++;
            }
        }

        double roundedResult = Math.ceil((thumbsUpCount / totalCount) * 100);

        Route route = routeDao.getRouteById(routeId);
        route.setRating((int) roundedResult);

        routeDao.saveRoute(route);
    }
}

