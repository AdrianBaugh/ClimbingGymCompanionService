package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetAllUsersClimbsRequest {

    private final String userId;

    private GetAllUsersClimbsRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetAllUsersClimbsRequest{" +
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
        public GetAllUsersClimbsRequest build() { return new GetAllUsersClimbsRequest(userId);
        }
    }
}
