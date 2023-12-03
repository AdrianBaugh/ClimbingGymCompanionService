package com.nashss.se.ClimbingGymCompanionService.models;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ClimbModel {
    private String climbId;
    private String userId;
    private String routeId;
    private String type;
    private String climbStatus;
    private ZonedDateTime dateTimeClimbed;
    private Boolean thumbsUp;
    private String notes;

    /**
     *
     * @param climbId the climb id
     * @param userId the user id
     * @param routeId the routeID
     * @param type of climb
     * @param climbStatus the climbStatus
     * @param dateTimeClimbed the dateTimeClimbed
     * @param thumbsUp the thumbsUp true or false
     * @param notes the notes
     */
    public ClimbModel(String climbId, String userId, String routeId, String type,
                      String climbStatus, ZonedDateTime dateTimeClimbed, Boolean thumbsUp, String notes) {
        this.climbId = climbId;
        this.userId = userId;
        this.routeId = routeId;
        this.type = type;
        this.climbStatus = climbStatus;
        this.dateTimeClimbed = dateTimeClimbed;
        this.thumbsUp = thumbsUp;
        this.notes = notes;
    }



    public String getClimbId() {
        return climbId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getType() {
        return type;
    }

    public String getClimbStatus() {
        return climbStatus;
    }

    public ZonedDateTime getDateTimeClimbed() {
        return dateTimeClimbed;
    }

    public Boolean getThumbsUp() {
        return thumbsUp;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ClimbModel that = (ClimbModel) other;
        return Objects.equals(climbId, that.climbId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(routeId, that.routeId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(climbStatus, that.climbStatus) &&
                Objects.equals(dateTimeClimbed, that.dateTimeClimbed) &&
                Objects.equals(thumbsUp, that.thumbsUp) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(climbId, userId, routeId, type, climbStatus, dateTimeClimbed, thumbsUp, notes);
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String climbId;
        private String userId;
        private String routeId;
        private String type;
        private String climbStatus;
        private ZonedDateTime dateTimeClimbed;
        private Boolean thumbsUp;
        private String notes;

        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withRouteId(String routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withClimbStatus(String climbStatus) {
            this.climbStatus = climbStatus;
            return this;
        }

        public Builder withDateTimeClimbed(ZonedDateTime dateTimeClimbed) {
            this.dateTimeClimbed = dateTimeClimbed;
            return this;
        }

        public Builder withThumbsUp(Boolean thumbsUp) {
            this.thumbsUp = thumbsUp;
            return this;
        }

        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public ClimbModel build() {
            return new ClimbModel(climbId, userId, routeId, type, climbStatus, dateTimeClimbed, thumbsUp, notes);
        }
    }
}
