package com.nashss.se.ClimbingGymCompanionService.exceptions;

/**
 * Exception to throw when a given route ID is not found in the database.
 */
public class RouteNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3294834060139465986L;

    /**
     * Exception with no message or cause.
     */
    public RouteNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public RouteNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public RouteNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public RouteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
