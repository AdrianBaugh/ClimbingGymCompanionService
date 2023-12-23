package com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos;

import com.nashss.se.ClimbingGymCompanionService.converters.StringIntegerMapConverter;
import com.nashss.se.ClimbingGymCompanionService.converters.WeeklyDifficultyFrequencyMapMapConverter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.Map;
import java.util.Objects;

@DynamoDBTable(tableName = "UserInfo")
public class UserInfo {
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

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "recentWeeklyClimbsFrequencyMap")
    @DynamoDBTypeConverted(converter = StringIntegerMapConverter.class)
    public Map<String, Integer> getRecentWeeklyClimbsFrequencyMap() {
        return recentWeeklyClimbsFrequencyMap;
    }

    public void setRecentWeeklyClimbsFrequencyMap(Map<String, Integer> recentWeeklyClimbsFrequencyMap) {
        this.recentWeeklyClimbsFrequencyMap = recentWeeklyClimbsFrequencyMap;
    }
    @DynamoDBAttribute(attributeName = "difficultyFrequencyMap")
    @DynamoDBTypeConverted(converter = StringIntegerMapConverter.class)
    public Map<String, Integer> getDifficultyFrequencyMap() {
        return difficultyFrequencyMap;
    }

    public void setDifficultyFrequencyMap(Map<String, Integer> difficultyFrequencyMap) {
        this.difficultyFrequencyMap = difficultyFrequencyMap;
    }
    @DynamoDBAttribute(attributeName = "weeklyDifficultyFrequencyMap")
    @DynamoDBTypeConverted(converter = WeeklyDifficultyFrequencyMapMapConverter.class)
    public Map<String, Map<String, Integer>> getWeeklyDifficultyFrequencyMap() {
        return weeklyDifficultyFrequencyMap;
    }

    public void setWeeklyDifficultyFrequencyMap(Map<String, Map<String, Integer>> weeklyDifficultyFrequencyMap) {
        this.weeklyDifficultyFrequencyMap = weeklyDifficultyFrequencyMap;
    }

    @DynamoDBAttribute(attributeName = "percentFlashedSentMap")
    @DynamoDBTypeConverted(converter = StringIntegerMapConverter.class)
    public Map<String, Integer> getPercentFlashedSentMap() {
        return percentFlashedSentMap;
    }

    public void setPercentFlashedSentMap(Map<String, Integer> percentFlashedSentMap) {
        this.percentFlashedSentMap = percentFlashedSentMap;
    }

    @DynamoDBAttribute(attributeName = "totalCompletedClimbs")
    public Integer getTotalCompletedClimbs() {
        return totalCompletedClimbs;
    }

    public void setTotalCompletedClimbs(Integer totalCompletedClimbs) {
        this.totalCompletedClimbs = totalCompletedClimbs;
    }

    @DynamoDBAttribute(attributeName = "stat6")
        public String getStat6() {
        return stat6;
    }

    public void setStat6(String stat6) {
        this.stat6 = stat6;
    }
    @DynamoDBAttribute(attributeName = "stat7")
    public String getStat7() {
        return stat7;
    }

    public void setStat7(String stat7) {
        this.stat7 = stat7;
    }
    @DynamoDBAttribute(attributeName = "stat8")
    public String getStat8() {
        return stat8;
    }

    public void setStat8(String stat8) {
        this.stat8 = stat8;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) other;
        return Objects.equals(userId, userInfo.userId) &&
                Objects.equals(recentWeeklyClimbsFrequencyMap, userInfo.recentWeeklyClimbsFrequencyMap) &&
                Objects.equals(difficultyFrequencyMap, userInfo.difficultyFrequencyMap) &&
                Objects.equals(weeklyDifficultyFrequencyMap, userInfo.weeklyDifficultyFrequencyMap) &&
                Objects.equals(percentFlashedSentMap, userInfo.percentFlashedSentMap) &&
                Objects.equals(totalCompletedClimbs, userInfo.totalCompletedClimbs) &&
                Objects.equals(stat6, userInfo.stat6) &&
                Objects.equals(stat7, userInfo.stat7) &&
                Objects.equals(stat8, userInfo.stat8);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recentWeeklyClimbsFrequencyMap, difficultyFrequencyMap,
                weeklyDifficultyFrequencyMap, percentFlashedSentMap, totalCompletedClimbs,
                stat6, stat7, stat8);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", recentWeeklyClimbsFrequencyMap=" + recentWeeklyClimbsFrequencyMap +
                ", difficultyFrequencyMap=" + difficultyFrequencyMap +
                ", weeklyDifficultyFrequencyMap=" + weeklyDifficultyFrequencyMap +
                ", percentFlashedSentMap=" + percentFlashedSentMap +
                ", totalCompletedClimbs=" + totalCompletedClimbs +
                ", stat6='" + stat6 + '\'' +
                ", stat7='" + stat7 + '\'' +
                ", stat8='" + stat8 + '\'' +
                '}';
    }
}
