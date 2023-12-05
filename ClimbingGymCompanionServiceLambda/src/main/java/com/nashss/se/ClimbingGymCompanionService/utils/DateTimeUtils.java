package com.nashss.se.ClimbingGymCompanionService.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    private DateTimeUtils() {};

    /**
     *
     * @return a time zoned time
     */
    public static ZonedDateTime getDateTime() {
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
    }
}
