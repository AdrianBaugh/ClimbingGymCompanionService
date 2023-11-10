package com.nashss.se.ClimbingGymCompanionService.activity.requests;

public class GetRouteRequest {
    private final String routeId;

    public GetRouteRequest(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }

    @Override
    public String toString() {
        return "GetRouteRequest{" +
                "routeId='" + routeId + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String routeId;
        public Builder withRouteId(String routeId){
            this.routeId = routeId;
            return this;
        }
        public GetRouteRequest build() {
            return new GetRouteRequest(routeId);
        }
    }
}
