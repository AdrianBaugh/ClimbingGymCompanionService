package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateClimbRequest.Builder.class)
public class CreateClimbRequest {
    private final String userId;
    private final String userName;
    private final String type;
    private final String routeId;
    private final String climbStatus;
    private final Boolean thumbsUp;
    private final String publicBeta;

    private CreateClimbRequest(String userId, String userName, String type, String routeId,
                              String climbStatus, Boolean thumbsUp, String publicBeta) {
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.routeId = routeId;
        this.climbStatus = climbStatus;
        this.thumbsUp = thumbsUp;
        this.publicBeta = publicBeta;
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

    public String getRouteId() {
        return routeId;
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
        return "CreateClimbRequest{" +
                "userId='" + userId + '\'' +
                "userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", routeId='" + routeId + '\'' +
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
        private String userId;
        private String userName;
        private String type;
        private String routeId;
        private String climbStatus;
        private Boolean thumbsUp;
        private String publicBeta;

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

        public Builder withPublicBeta(String publicBeta) {
            this.publicBeta = publicBeta;
            return this;
        }

        public CreateClimbRequest build() {
            return new CreateClimbRequest(userId, userName, type, routeId, climbStatus, thumbsUp, publicBeta);
        }
    }
}
