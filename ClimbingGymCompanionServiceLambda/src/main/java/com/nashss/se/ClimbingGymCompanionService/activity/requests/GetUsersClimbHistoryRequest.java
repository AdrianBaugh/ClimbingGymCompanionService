package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetUsersClimbHistoryRequest {

    private final String userId;

    private GetUsersClimbHistoryRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetUsersClimbHistoryRequest{" +
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
        public GetUsersClimbHistoryRequest build() { return new GetUsersClimbHistoryRequest(userId);
        }
    }
}
