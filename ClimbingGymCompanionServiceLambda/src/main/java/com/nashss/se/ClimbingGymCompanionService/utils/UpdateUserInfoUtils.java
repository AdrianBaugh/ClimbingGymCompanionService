package com.nashss.se.ClimbingGymCompanionService.utils;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;
import com.nashss.se.ClimbingGymCompanionService.enums.ClimbStatus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class UpdateUserInfoUtils {

    private static final Integer NUMBER_OF_RECENT_WEEKS = 5;
    private UpdateUserInfoUtils() {
    }

    /**
     * Update the stats package of all user info elements.
     * @param userInfoDao to access the userInfo metadata.
     * @param routeDao to access the route metadata.
     * @param climb the current climb.
     */
    public static void updateUserInfo(UserInfoDao userInfoDao, RouteDao routeDao, Climb climb) {
        UserInfo userInfo;
        Route route = routeDao.getRouteById(climb.getRouteId());
        try {
            userInfo = userInfoDao.getUserInfoById(climb.getUserId());
        } catch (RuntimeException e) {
            userInfo = new UserInfo();
            userInfo.setUserId(climb.getUserId());
            userInfo.setTotalCompletedClimbs(0);
            userInfo.setRecentWeeklyClimbsFrequencyMap(new TreeMap<>());
            userInfo.setDifficultyFrequencyMap(new TreeMap<>());
            userInfo.setWeeklyDifficultyFrequencyMap(new TreeMap<>());
            userInfo.setPercentFlashedSentMap(new HashMap<>());
        }

        //call each update stat method
        // total completed climbs
        userInfo.setTotalCompletedClimbs(updateTotalCompletedClimbs(userInfo, climb));

        // recent weekly climb frequency map
        userInfo.setRecentWeeklyClimbsFrequencyMap(updateRecentWeeklyClimbFrequencyMap(userInfo, climb));

        // total climbs by difficulty frequency map
        userInfo.setDifficultyFrequencyMap(updateDifficultyFrequencyMap(route, userInfo, climb));

        // weekly climb difficulty frequency map
        userInfo.setWeeklyDifficultyFrequencyMap(updateWeeklyDifficultyMap(route, userInfo, climb));

        userInfoDao.saveUserInfo(userInfo);
    }

    // I N C O M P L E T E
    // *********************************************************
    /**
     * Updates the map to keep track of the difficulties climbed per week
     * @param route to access route's difficulty
     * @param userInfo the userInfo object to update
     * @param climb the current climb data
     * @return the weekly difficulty frequency map
     */
    private static Map<String, Map<String, Integer>>  updateWeeklyDifficultyMap(
            Route route, UserInfo userInfo, Climb climb) {

        Map<String, Map<String, Integer>> fullMap = userInfo.getWeeklyDifficultyFrequencyMap();
        String currYearWeek = climb.getWeekClimbed();
        String difficulty = route.getDifficulty();

        Map<String, Integer> currMap = fullMap.getOrDefault(currYearWeek, new HashMap<>());

        currMap.put(difficulty, currMap.getOrDefault(difficulty, 0) + 1);

        fullMap.put(currYearWeek, currMap);
        //fill missing weeks maybe with the earliest week as start and then up to the current week as end

        //get most recent DATA(last 5 weeks only)?

        return fullMap;
    }

    /**
     * Updates the all-time total count for each completed climb of a specific difficulty.
     * @param route to access route's difficulty
     * @param userInfo the userInfo object to update
     * @param climb the current climb data
     * @return the frequency difficulty map
     */
    private static Map<String, Integer> updateDifficultyFrequencyMap(Route route, UserInfo userInfo, Climb climb) {

        Map<String, Integer> currMap = userInfo.getDifficultyFrequencyMap();

        String status = climb.getClimbStatus();

        if (status.contains("COMPLETED")) {
            String difficulty = route.getDifficulty();

            currMap.put(difficulty, currMap.getOrDefault(difficulty, 0) + 1);
        }

        return currMap;
    }

    /**
     * Updates the total completed/attempted climbs per week for the last 5 weeks.
     * @param userInfo to update
     * @param climb to get metadata from
     * @return the frequency map
     */
    private static Map<String, Integer> updateRecentWeeklyClimbFrequencyMap(UserInfo userInfo, Climb climb) {

        TreeMap<String, Integer> currMap = new TreeMap<>(userInfo.getRecentWeeklyClimbsFrequencyMap());

        String weekClimbed = climb.getWeekClimbed();

        if (!climb.getClimbStatus().equals(String.valueOf(ClimbStatus.WANT_TO_CLIMB))) {
            currMap.put(weekClimbed, currMap.getOrDefault(weekClimbed, 0) + 1);

            int firstYear = Integer.parseInt(currMap.firstEntry().getKey().split("::")[0]);

            fillMissingWeeks(currMap, firstYear);
        }

        return getMostRecentData(currMap);
    }

    /**
     * Helper method to fill in missing weeks with a value of 0
     * @param map to add fill missing weeks in
     * @param startYear the year to begin adding the missing week data
     */
    private static void fillMissingWeeks(Map<String, Integer> map, int startYear) {

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        for (int year = startYear; year <= currentYear; year++) {
            int maxWeek = (year == currentYear) ? currentWeek : 52;
            int minWeek = (year == startYear) ? calendar.getActualMinimum(Calendar.WEEK_OF_YEAR) : 1;

            for (int week = minWeek; week <= maxWeek; week++) {
                String yearWeek = String.format("%04d::%02d", year, week);
                map.putIfAbsent(yearWeek, 0);
            }
        }
    }

    /**
     * Helper method to get the final TreeMap containing the most recent entries.
     * @param map The map to extract the recent data from
     * @return the map of recent data
     */
    private static Map<String, Integer> getMostRecentData(Map<String, Integer> map) {

        NavigableMap<String, Integer> descendingMap = new TreeMap<>(map).descendingMap();

        return new TreeMap<>(descendingMap).entrySet().stream()
                .limit(NUMBER_OF_RECENT_WEEKS)
                .collect(TreeMap::new, (key, value) -> key.put(value.getKey(), value.getValue()), Map::putAll);
    }

    /**
     * Helper to update the total completed climbs stat.
     * @param userInfo obj to update.
     * @param climb to check completed status and update with.
     * @return the updated count
     */
    private static Integer updateTotalCompletedClimbs(UserInfo userInfo, Climb climb) {
        Integer count = userInfo.getTotalCompletedClimbs();

        String status = climb.getClimbStatus();

        if (status.contains("COMPLETED")) {
            count++;
        }
        return count;
    }
}
