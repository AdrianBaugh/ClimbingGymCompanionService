package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.UserInfoModel;

public class GetUserInfoResult {
    private final UserInfoModel userInfoModel;

    private GetUserInfoResult(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    @Override
    public String toString() {
        return "GetUserInfoResult{" +
                "userInfoModel=" + userInfoModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private UserInfoModel userInfoModel;

        public Builder withUserInfoModel(UserInfoModel userInfoModel) {
            this.userInfoModel = userInfoModel;
            return this;
        }
        public GetUserInfoResult build() { return new GetUserInfoResult(userInfoModel); }
    }
}
