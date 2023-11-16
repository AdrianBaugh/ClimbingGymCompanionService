package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateClimbRequest.Builder.class)
public class CreateClimbRequest {
    private final String userId;
    private final String routeId;
    private final String climbStatus;
    private final Boolean thumbsUp;
    private final String notes;

    private CreateClimbRequest(String userId, String routeId, String climbStatus, Boolean thumbsUp, String notes) {
        this.userId = userId;
        this.routeId = routeId;
        this.climbStatus = climbStatus;
        this.thumbsUp = thumbsUp;
        this.notes = notes;
    }

    public String getUserId() {
        return userId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getClimbStatus() {
        return climbStatus;
    }

    public Boolean getThumbsUp() {
        return thumbsUp;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "CreateClimbRequest{" +
                "userId='" + userId + '\'' +
                ", routeId='" + routeId + '\'' +
                ", climbStatus='" + climbStatus + '\'' +
                ", thumbsUp=" + thumbsUp +
                ", notes='" + notes + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String routeId;
        private String climbStatus;
        private Boolean thumbsUp;
        private String notes;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withRouteId(String routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder withClimbStatus(String climbStatus) {
            this.climbStatus = climbStatus;
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

        public CreateClimbRequest build() {
            return new CreateClimbRequest(userId, routeId, climbStatus, thumbsUp, notes);
        }
    }
}
