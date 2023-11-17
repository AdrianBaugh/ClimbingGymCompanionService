package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

public class UpdateRouteStatusResult {
    private final RouteModel route;

    private UpdateRouteStatusResult(RouteModel route) {
        this.route = route;
    }

    public RouteModel getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "UpdateRouteStatusResult{" +
                "route=" + route +
                '}';
    }
    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RouteModel route;

        public Builder withRoute(RouteModel route) {
            this.route = route;
            return this;
        }

        public UpdateRouteStatusResult build() { return new UpdateRouteStatusResult(route);}
    }
}
