package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

public class GetRouteResult {
    private final RouteModel route;

    private GetRouteResult(RouteModel route) {
        this.route = route;
    }

    public RouteModel getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "GetRouteResult{" +
                "route=" + route +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private RouteModel route;

        public Builder withRoute(RouteModel route) {
            this.route = route;
            return this;
        }
        public GetRouteResult build() { return new GetRouteResult(route); }
    }
}
