package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetAllArchivedRoutesRequest {
    private final String isArchived;

    private GetAllArchivedRoutesRequest(String isArchived) {
        this.isArchived = isArchived;
    }

    public String getIsArchived() {
        return isArchived;
    }

    @Override
    public String toString() {
        return "GetAllArchivedRoutesRequest{" +
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
        public GetAllArchivedRoutesRequest build() {
            return new GetAllArchivedRoutesRequest(isArchived);
        }
    }
}
