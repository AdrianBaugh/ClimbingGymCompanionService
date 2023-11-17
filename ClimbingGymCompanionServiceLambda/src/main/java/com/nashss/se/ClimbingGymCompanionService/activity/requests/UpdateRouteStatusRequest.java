package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateRouteStatusRequest.Builder.class)
public class UpdateRouteStatusRequest {
    private final String routeId;
    private final String routeStatus;

    private UpdateRouteStatusRequest(String routeId, String routeStatus) {
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
        return "UpdateRouteStatusRequest{" +
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

        public UpdateRouteStatusRequest build() {
            return new UpdateRouteStatusRequest(routeId, routeStatus);
        }

    }
}
