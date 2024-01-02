package com.nashss.se.ClimbingGymCompanionService.utils;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.RouteDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;
import com.nashss.se.ClimbingGymCompanionService.enums.ClimbStatus;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class UpdateUserInfoUtils {

    private static final Integer NUMBER_OF_RECENT_WEEKS = 5;
    private UpdateUserInfoUtils() {
    }

    public static void updateUserInfo(UserInfoDao userInfoDao, RouteDao routeDao, Climb climb) {
        UserInfo userInfo;
        Route route = routeDao.getRouteById(climb.getRouteId());
        try {
            userInfo = userInfoDao.getUserInfoById(climb.getUserId());
        } catch (RuntimeException e) {
            userInfo = new UserInfo();
            userInfo.setUserId(climb.getUserId());
            userInfo.setRecentWeeklyClimbsFrequencyMap(new TreeMap<>());
            userInfo.setDifficultyFrequencyMap(new HashMap<>());
            userInfo.setWeeklyDifficultyFrequencyMap(new TreeMap<>());
            userInfo.setPercentFlashedSentMap(new HashMap<>());
            userInfo.setTotalCompletedClimbs(0);
        }

        //call each update stat method
        // recent weekly climb frequency map
        userInfo.setRecentWeeklyClimbsFrequencyMap(updateWeeklyClimbFrequencyMap(userInfo, climb));

        // total climbs by difficulty frequency map
        userInfo.setDifficultyFrequencyMap(updateDifficultyFrequencyMap(route, userInfo, climb));

        // weekly climb frequency map
        userInfo.setWeeklyDifficultyFrequencyMap(updateWeeklyDifficultyMap(route, userInfo, climb));
        userInfoDao.saveUserInfo(userInfo);
    }

    private static Map<String, Map<String, Integer>>  updateWeeklyDifficultyMap(
            Route route, UserInfo userInfo, Climb climb) {

        Map<String, Map<String, Integer>> fullMap = userInfo.getWeeklyDifficultyFrequencyMap();
        String currYearWeek = climb.getWeekClimbed();
        String difficulty = route.getDifficulty();

        Map<String, Integer> currMap = fullMap.getOrDefault(currYearWeek, new HashMap<>());

        currMap.put(difficulty, currMap.getOrDefault(difficulty, 0) + 1);

        fullMap.put(currYearWeek, currMap);
        //fill missing weeks maybe with the earliest week as start and then up to the current week as end

        //get most recent

        return fullMap;
    }

    /**
     * Updates the all time total count for each completed climb of a specific difficulty.
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
     * Updates the total climbs per week fr the last 5 weeks
     * @param userInfo to update
     * @param climb to get metadata from
     * @return the frequency map
     */
    private static Map<String, Integer> updateWeeklyClimbFrequencyMap(UserInfo userInfo, Climb climb) {
        //get the map
        Map<String, Integer> currMap = userInfo.getRecentWeeklyClimbsFrequencyMap();

        String yearWeek = climb.getWeekClimbed();

        currMap.put(yearWeek, currMap.getOrDefault(yearWeek, 0) + 1);

        int currentYear = Integer.parseInt(yearWeek.split("::")[0]);
        // need to verify this returns the first element in the treemap
        int firstYear = Integer.parseInt(String.valueOf(currMap.keySet().stream().findFirst()));
        //int endYear = LocalDate.now().getYear();

        fillMissingWeeks(currMap, firstYear, currentYear);

        Map<String, Integer> recentDataMap = getMostRecentData(currMap);

        return recentDataMap;
    }

    // Helper method to fill in missing weeks with a value of 0
    private static void fillMissingWeeks(Map<String, Integer> map, int startYear, int endYear) {
        int startWeek = 1;
        int endWeek = 52;

        for (int year = startYear; year <= endYear; year++) {
            int maxWeek = (year == endYear) ? endWeek : 52;
            int minWeek = (year == startYear) ? startWeek : 1;

            for (int week = minWeek; week <= maxWeek; week++) {
                String yearWeek = String.format("%04d::%02d", year, week);
                map.putIfAbsent(yearWeek, 0);
            }
        }
    }

    /**
     * Helper method to get the final TreeMap containing the most recent entries
     * @param map The map to extract the recent data from
     * @return the map of recent data
     */
    private static Map<String, Integer> getMostRecentData(Map<String, Integer> map) {
        NavigableMap<String, Integer> descendingMap = new TreeMap<>(map).descendingMap();

        return new TreeMap<>(descendingMap).entrySet().stream()
                .limit(NUMBER_OF_RECENT_WEEKS)
                .collect(TreeMap::new, (key, value) -> key.put(value.getKey(), value.getValue()), Map::putAll);
    }
}
