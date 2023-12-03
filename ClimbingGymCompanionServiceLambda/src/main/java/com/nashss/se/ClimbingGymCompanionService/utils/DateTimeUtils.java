package com.nashss.se.ClimbingGymCompanionService.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    public static ZonedDateTime getDateTime() {
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
    }
}
