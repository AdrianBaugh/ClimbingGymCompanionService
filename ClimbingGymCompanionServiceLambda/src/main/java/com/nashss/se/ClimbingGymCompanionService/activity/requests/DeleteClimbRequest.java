package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class DeleteClimbRequest {
    private final String userId;
    private final String climbId;

    private DeleteClimbRequest(String userId, String climbId) {
        this.userId = userId;
        this.climbId = climbId;
    }

    public String getUserId() {
        return userId;
    }

    public String getClimbId() {
        return climbId;
    }

    @Override
    public String toString() {
        return "DeleteClimbRequest{" +
                "userId='" + userId + '\'' +
                ", climbId='" + climbId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String userId;
        private String climbId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }

        public DeleteClimbRequest build() {
            return new DeleteClimbRequest(userId, climbId);
        }
    }
}
