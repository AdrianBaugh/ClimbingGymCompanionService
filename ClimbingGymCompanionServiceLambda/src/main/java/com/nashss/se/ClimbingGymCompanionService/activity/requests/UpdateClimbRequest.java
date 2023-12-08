package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateClimbRequest.Builder.class)
public class UpdateClimbRequest {
    private final String climbId;
    private final String userId;
    private final String userName;
    private final String type;
    private final String climbStatus;
    private final Boolean thumbsUp;
    private final String publicBeta;

    private UpdateClimbRequest(String climbId, String userId, String userName, String type,
                               String climbStatus, Boolean thumbsUp, String publicBeta) {
        this.climbId = climbId;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.climbStatus = climbStatus;
        this.thumbsUp = thumbsUp;
        this.publicBeta = publicBeta;
    }

    public String getClimbId() {
        return climbId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
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

    public String getPublicBeta() {
        return publicBeta;
    }

    @Override
    public String toString() {
        return "UpdateClimbRequest{" +
                "climbId='" + climbId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", climbStatus='" + climbStatus + '\'' +
                ", thumbsUp=" + thumbsUp +
                ", publicBeta='" + publicBeta + '\'' +
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
        private String userName;
        private String type;
        private String climbStatus;
        private Boolean thumbsUp;
        private String publicBeta;

        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withUserName(String userName) {
            this.userName = userName;
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

        public Builder withPublicBeta(String publicBeta) {
            this.publicBeta = publicBeta;
            return this;
        }

        public UpdateClimbRequest build() {
            return new UpdateClimbRequest(climbId, userId, userName, type, climbStatus, thumbsUp, publicBeta);
        }
    }
}
