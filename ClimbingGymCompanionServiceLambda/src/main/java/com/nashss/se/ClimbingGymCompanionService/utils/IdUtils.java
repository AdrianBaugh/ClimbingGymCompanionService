package com.nashss.se.ClimbingGymCompanionService.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class IdUtils {
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");
    private static final int MAX_ID_LENGTH = 10;

    private IdUtils() {
    }

    /**
     *
     * @param stringToValidate string to check
     * @return if the string is valid or not boolean
     */
    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }

    /**
     * Key Structure: location:: + color:: + dateCreated.
     * top rope/lead location = number.
     * bouldering location = wall location (slab, island, cave).
     * @param location of the route
     * @param color of the route
     * @param date route was created
     * @return the key and routeID
     */
    public static String generateRouteId(String location, String color, LocalDate date) {
        return location + "::" + color + "::" + date;
    }

    /**
     *  key Structure randomAlphanumeric(MAX_ID_LENGTH):: + dateTime.
     * @param dateTime of the climbId generation
     * @return the id
     */
    public static String generateClimbId(LocalDateTime dateTime) {
        return  RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH) + "::" + dateTime;
    }
}
