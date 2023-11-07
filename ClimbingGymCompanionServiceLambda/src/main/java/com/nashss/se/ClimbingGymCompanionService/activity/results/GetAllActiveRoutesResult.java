package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllActiveRoutesResult {
    private final List<RouteModel> routeList;

    public GetAllActiveRoutesResult(List<RouteModel> routeList) {
        this.routeList = routeList;
    }

    public List<RouteModel> getRouteList() {
        return routeList;
    }

    @Override
    public String toString() {
        return "GetAllActiveRoutesResult{" +
                "routeList=" + routeList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<RouteModel> routeList;

        public Builder withRouteList(List<RouteModel> routeList) {
            this.routeList = new ArrayList<>(routeList);
            return this;
        }
        public GetAllActiveRoutesResult build() { return new GetAllActiveRoutesResult(routeList);}
    }
}
