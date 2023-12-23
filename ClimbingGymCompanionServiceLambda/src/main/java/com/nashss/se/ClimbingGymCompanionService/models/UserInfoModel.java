package com.nashss.se.ClimbingGymCompanionService.models;

import java.util.Map;
import java.util.Objects;

public class UserInfoModel {
    private String userId;
    private Map<String, Integer> recentWeeklyClimbsFrequencyMap;
    private Map<String, Integer>  difficultyFrequencyMap;

    //<k,v> = <week, <difficulty, frequency>>
    private Map<String, Map<String, Integer>>  weeklyDifficultyFrequencyMap;
    private Map<String, Integer> percentFlashedSentMap;
    private Integer totalCompletedClimbs;
    private String stat6;
    private String stat7;
    private String stat8;

    /**
     *
     * @param userId userInfo model metadata
     * @param recentWeeklyClimbsFrequencyMap userInfo model metadata
     * @param difficultyFrequencyMap userInfo model metadata
     * @param weeklyDifficultyFrequencyMap userInfo model metadata
     * @param percentFlashedSentMap userInfo model metadata
     * @param totalCompletedClimbs userInfo model metadata
     * @param stat6 userInfo model metadata
     * @param stat7 userInfo model metadata
     * @param stat8 userInfo model metadata
     */
    private UserInfoModel(String userId,
                         Map<String, Integer> recentWeeklyClimbsFrequencyMap,
                         Map<String, Integer> difficultyFrequencyMap,
                         Map<String, Map<String, Integer>> weeklyDifficultyFrequencyMap,
                         Map<String, Integer> percentFlashedSentMap,
                         Integer totalCompletedClimbs,
                         String stat6, String stat7, String stat8) {
        this.userId = userId;
        this.recentWeeklyClimbsFrequencyMap = recentWeeklyClimbsFrequencyMap;
        this.difficultyFrequencyMap = difficultyFrequencyMap;
        this.weeklyDifficultyFrequencyMap = weeklyDifficultyFrequencyMap;
        this.percentFlashedSentMap = percentFlashedSentMap;
        this.totalCompletedClimbs = totalCompletedClimbs;
        this.stat6 = stat6;
        this.stat7 = stat7;
        this.stat8 = stat8;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Integer> getRecentWeeklyClimbsFrequencyMap() {
        return recentWeeklyClimbsFrequencyMap;
    }

    public Map<String, Integer> getDifficultyFrequencyMap() {
        return difficultyFrequencyMap;
    }

    public Map<String, Map<String, Integer>> getWeeklyDifficultyFrequencyMap() {
        return weeklyDifficultyFrequencyMap;
    }

    public Map<String, Integer> getPercentFlashedSentMap() {
        return percentFlashedSentMap;
    }

    public Integer getTotalCompletedClimbs() {
        return totalCompletedClimbs;
    }

    public String getStat6() {
        return stat6;
    }

    public String getStat7() {
        return stat7;
    }

    public String getStat8() {
        return stat8;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UserInfoModel that = (UserInfoModel) other;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(recentWeeklyClimbsFrequencyMap, that.recentWeeklyClimbsFrequencyMap) &&
                Objects.equals(difficultyFrequencyMap, that.difficultyFrequencyMap) &&
                Objects.equals(weeklyDifficultyFrequencyMap, that.weeklyDifficultyFrequencyMap) &&
                Objects.equals(percentFlashedSentMap, that.percentFlashedSentMap) &&
                Objects.equals(totalCompletedClimbs, that.totalCompletedClimbs) &&
                Objects.equals(stat6, that.stat6) &&
                Objects.equals(stat7, that.stat7) &&
                Objects.equals(stat8, that.stat8);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recentWeeklyClimbsFrequencyMap, difficultyFrequencyMap,
                weeklyDifficultyFrequencyMap, percentFlashedSentMap, totalCompletedClimbs,
                stat6, stat7, stat8);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private Map<String, Integer> recentWeeklyClimbsFrequencyMap;
        private Map<String, Integer>  difficultyFrequencyMap;

        //<k,v> = <week, <difficulty, frequency>>
        private Map<String, Map<String, Integer>>  weeklyDifficultyFrequencyMap;
        private Map<String, Integer> percentFlashedSentMap;
        private Integer totalCompletedClimbs;
        private String stat6;
        private String stat7;
        private String stat8;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withRecentWeeklyClimbsFrequencyMap(Map<String, Integer> recentWeeklyClimbsFrequencyMap) {
            this.recentWeeklyClimbsFrequencyMap = recentWeeklyClimbsFrequencyMap;
            return this;
        }

        public Builder withDifficultyFrequencyMap(Map<String, Integer> difficultyFrequencyMap) {
            this.difficultyFrequencyMap = difficultyFrequencyMap;
            return this;
        }

        public Builder withWeeklyDifficultyFrequencyMap(Map<String, Map<String, Integer>> weeklyDifficultyFrequencyMap) {
            this.weeklyDifficultyFrequencyMap = weeklyDifficultyFrequencyMap;
            return this;
        }

        public Builder withPercentFlashedSentMap(Map<String, Integer> percentFlashedSentMap) {
            this.percentFlashedSentMap = percentFlashedSentMap;
            return this;
        }

        public Builder withTotalCompletedClimbs(Integer totalCompletedClimbs) {
            this.totalCompletedClimbs = totalCompletedClimbs;
            return this;
        }

        public Builder withStat6(String stat6) {
            this.stat6 = stat6;
            return this;
        }

        public Builder withStat7(String stat7) {
            this.stat7 = stat7;
            return this;
        }

        public Builder withStat8(String stat8) {
            this.stat8 = stat8;
            return this;
        }

        public UserInfoModel build() {
            return new UserInfoModel(userId, recentWeeklyClimbsFrequencyMap, difficultyFrequencyMap,
                    weeklyDifficultyFrequencyMap, percentFlashedSentMap, totalCompletedClimbs,
                    stat6, stat7, stat8);
        }
    }
}
