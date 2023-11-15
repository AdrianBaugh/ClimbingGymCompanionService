package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateRouteRequest.Builder.class)
public class UpdateRouteRequest {
    private final String routeId;
    private final String routeStatus;

    private UpdateRouteRequest(String routeId, String routeStatus) {
        this.routeId = routeId;
        this.routeStatus = routeStatus;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    @Override
    public String toString() {
        return "UpdateRouteRequest{" +
                "routeId='" + routeId + '\'' +
                ", routeStatus='" + routeStatus + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String routeId;
        private String routeStatus;

        public Builder withRouteId(String routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder withRouteStatus(String routeStatus) {
            this.routeStatus = routeStatus;
            return this;
        }

        public UpdateRouteRequest build() {
            return new UpdateRouteRequest(routeId, routeStatus);
        }

    }
}
