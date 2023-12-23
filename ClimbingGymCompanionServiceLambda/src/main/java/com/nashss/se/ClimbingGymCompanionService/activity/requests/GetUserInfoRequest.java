package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetUserInfoRequest {
    private final String userId;

    private GetUserInfoRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetUserInfoRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public GetUserInfoRequest build() { return new GetUserInfoRequest(userId); }
    }
}
