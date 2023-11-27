package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateClimbRequest.Builder.class)
public class UpdateClimbRequest {
    private final String climbId;
    private final String userId;
    private final String type;
    private final String climbStatus;
    private final Boolean thumbsUp;
    private final String notes;

    private UpdateClimbRequest(String climbId, String userId, String type,
                               String climbStatus, Boolean thumbsUp, String notes) {
        this.climbId = climbId;
        this.userId = userId;
        this.type = type;
        this.climbStatus = climbStatus;
        this.thumbsUp = thumbsUp;
        this.notes = notes;
    }

    public String getClimbId() {
        return climbId;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
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
        return "UpdateClimbRequest{" +
                "climbId='" + climbId + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
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
        private String climbId;
        private String userId;
        private String type;
        private String climbStatus;
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

        public Builder withType(String type) {
            this.type = type;
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

        public UpdateClimbRequest build() {
            return new UpdateClimbRequest(climbId, userId, type, climbStatus, thumbsUp, notes);
        }
    }
}
