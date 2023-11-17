package com.nashss.se.ClimbingGymCompanionService.exceptions;

/**
 * Exception to throw when a given climb ID is not found in the database.
 */
public class ArchivedStatusNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8707423639973404137L;
    /**
     * Exception with no message or cause.
     */
    public ArchivedStatusNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ArchivedStatusNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ArchivedStatusNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ArchivedStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
