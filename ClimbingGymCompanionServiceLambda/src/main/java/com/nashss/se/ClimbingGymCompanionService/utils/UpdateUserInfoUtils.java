package com.nashss.se.ClimbingGymCompanionService.utils;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.UserInfoDao;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;

public class UpdateUserInfoUtils {
    private UpdateUserInfoUtils() {
    }

    public static void updateWeeklyClimbFrequencyMap(UserInfoDao userInfoDao, Climb climb) {
        //get the map
        UserInfo userInfo = userInfoDao.getUserInfoById(climb.getUserId());
        Map<String, Integer> currMap = userInfo.getRecentWeeklyClimbsFrequencyMap();

        String yearWeek = climb.getWeekClimbed();

        if (currMap == null) {
            currMap = new TreeMap<>();
        }

        incrementFrequency(currMap, yearWeek);

        int currentYear = Integer.parseInt(yearWeek.split("::")[0]);
        int endYear = LocalDate.now().getYear();

        // Fill in missing weeks with a value of 0
        fillMissingWeeks(currMap, currentYear, endYear);

        Map<String, Integer> recentDataMap = getMostRecentData(currMap, 5);

    }

    // Helper method to increment the frequency for a given YearWeek
    private static void incrementFrequency(Map<String, Integer> map, String yearWeek) {
        map.put(yearWeek, map.getOrDefault(yearWeek, 0) + 1);
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

    // Helper method to get the final TreeMap containing the most recent entries
    private static Map<String, Integer> getMostRecentData(Map<String, Integer> map, int numEntries) {
        TreeMap<String, Integer> result = new TreeMap<>(Collections.reverseOrder());
        NavigableSet<String> descendingKeys = map.descendingKeySet();
        Iterator<String> iterator = descendingKeys.iterator();

        for (int i = 0; i < numEntries && iterator.hasNext(); i++) {
            Map.Entry<String, Integer> entry = iterator.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
