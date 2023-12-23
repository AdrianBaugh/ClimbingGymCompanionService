package com.nashss.se.ClimbingGymCompanionService.utils;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class UpdateUserInfoUtils {

    private static final Integer NUMBER_OF_RECENT_WEEKS = 5;
    private UpdateUserInfoUtils() {
    }

    public static void updateUserInfo(UserInfoDao userInfoDao, Climb climb) {
        UserInfo userInfo;
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
        userInfo.setRecentWeeklyClimbsFrequencyMap(updateWeeklyClimbFrequencyMap(userInfo, climb));

        userInfoDao.saveUserInfo(userInfo);
    }

    private static Map<String, Integer> updateWeeklyClimbFrequencyMap(UserInfo userInfo, Climb climb) {
        //get the map
        Map<String, Integer> currMap = userInfo.getRecentWeeklyClimbsFrequencyMap();

        String yearWeek = climb.getWeekClimbed();

        currMap.put(yearWeek, currMap.getOrDefault(yearWeek, 0) + 1);

        int currentYear = Integer.parseInt(yearWeek.split("::")[0]);
        int endYear = LocalDate.now().getYear();

        fillMissingWeeks(currMap, currentYear, endYear);

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
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }
}
