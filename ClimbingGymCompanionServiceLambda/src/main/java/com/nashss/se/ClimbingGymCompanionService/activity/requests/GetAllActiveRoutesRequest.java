package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetAllActiveRoutesRequest {
    private final String isArchived;

    public GetAllActiveRoutesRequest(String isArchived) {
        this.isArchived = isArchived;
    }

    public String getIsArchived() {
        return isArchived;
    }

    @Override
    public String toString() {
        return "GetAllActiveRoutesRequest{" +
                "isArchived='" + isArchived + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String isArchived;
        public Builder withIsArchived(String isArchived){
            this.isArchived = isArchived;
            return this;
        }
        public GetAllActiveRoutesRequest build() {
            return new GetAllActiveRoutesRequest(isArchived);
        }
    }
}
