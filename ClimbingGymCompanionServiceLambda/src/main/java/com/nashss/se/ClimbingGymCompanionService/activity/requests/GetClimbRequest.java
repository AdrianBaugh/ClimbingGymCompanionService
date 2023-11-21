package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetClimbRequest {
    private final String climbId;
    private final String userId;

    private GetClimbRequest(String climbId, String userId) {
        this.climbId = climbId;
        this.userId = userId;
    }

    public String getClimbId() {
        return climbId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetClimbRequest{" +
                "climbId='" + climbId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String climbId;
        private String userId;

        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetClimbRequest build() { return new GetClimbRequest(climbId, userId); }
    }
}
