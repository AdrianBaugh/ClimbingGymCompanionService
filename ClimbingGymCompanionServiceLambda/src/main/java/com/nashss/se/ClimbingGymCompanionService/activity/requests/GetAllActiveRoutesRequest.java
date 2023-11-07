package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetAllActiveRoutesRequest {
    private final String excludedStatus;

    public GetAllActiveRoutesRequest(String excludedStatus) {
        this.excludedStatus = excludedStatus;
    }

    public String getExcludedStatus() {
        return excludedStatus;
    }

    @Override
    public String toString() {
        return "GetAllActiveRoutesRequest{" +
                "excludedStatus='" + excludedStatus + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String excludedStatus;
        public Builder withExcludedStatus(String excludedStatus){
            this.excludedStatus = excludedStatus;
            return this;
        }
        public GetAllActiveRoutesRequest build() {
            return new GetAllActiveRoutesRequest(excludedStatus);
        }
    }
}
