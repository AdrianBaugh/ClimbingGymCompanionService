package com.nashss.se.ClimbingGymCompanionService.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class DateTimeUtils {

    private DateTimeUtils() {};

    /**
     *
     * @return a time zoned time
     */
    public static ZonedDateTime getDateTime() {
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
    }

    /**
     *
     * @return week number for climb stats
     */
    public static String getWeekOfYear() {
        Calendar now = Calendar.getInstance();
        int year = now.getWeekYear();
        return year + "::" + String.valueOf(now.get(Calendar.WEEK_OF_YEAR));
    }
}
