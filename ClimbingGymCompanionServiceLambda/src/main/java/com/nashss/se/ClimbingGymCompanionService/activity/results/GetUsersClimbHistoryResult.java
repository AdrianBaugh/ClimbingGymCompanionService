package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

import java.util.List;

public class GetUsersClimbHistoryResult {
    private final List<ClimbModel> climbList;

    private GetUsersClimbHistoryResult(List<ClimbModel> climbList) {
        this.climbList = climbList;
    }

    public List<ClimbModel> getClimbList() {
        return climbList;
    }

    @Override
    public String toString() {
        return "GetUsersClimbHistoryResult{" +
                "climbList=" + climbList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<ClimbModel> climbList;

        public Builder withClimbList(List<ClimbModel> climbList) {
            this.climbList = climbList;
            return this;
        }

        public GetUsersClimbHistoryResult build() { return new GetUsersClimbHistoryResult(climbList); }
    }

}
