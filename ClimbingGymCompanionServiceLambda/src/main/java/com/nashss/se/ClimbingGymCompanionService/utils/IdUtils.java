package com.nashss.se.ClimbingGymCompanionService.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class IdUtils {
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");
    static final int MAX_ID_LENGTH = 10;

    private IdUtils() {
    }

    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }
    /*
    * Sort Key Structure: location:: + color:: + dateCreated
    * top rope/lead location = number
    * bouldering location = wall location (slab, island, cave)
     */
    public static String generateRouteId(String location, String color, LocalDate date) {
        return location + "::" + color + "::" + date;
    }

    /*
    *  key Structure randomAlphanumeric(MAX_ID_LENGTH):: + dateTime
     */
    public static String generateClimbId(LocalDateTime dateTime) {
        return  RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH) + "::" + dateTime;
    }
}