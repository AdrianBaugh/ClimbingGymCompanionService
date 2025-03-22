package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.RouteModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllArchivedRoutesResult {
private final List<RouteModel> routeList;

        private GetAllArchivedRoutesResult(List<RouteModel> routeList) {
            this.routeList = routeList;
        }

        public List<RouteModel> getRouteList() {
            return routeList;
        }

        @Override
        public String toString() {
            return "GetAllArchivedRoutesResult{" +
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
    public GetAllArchivedRoutesResult build() { return new GetAllArchivedRoutesResult(routeList);}
}
}
