package com.nashss.se.ClimbingGymCompanionService.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class WeeklyDifficultyFrequencyMapMapConverter
        implements DynamoDBTypeConverter<String, Map<String, Map<String, Integer>>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Turns an object of type T into an object of type S.
     *
     * @param weeklyDifficultyFrequencyMap to be converted
     */
    @Override
    public String convert(Map<String, Map<String, Integer>> weeklyDifficultyFrequencyMap) {
        try {
            return objectMapper.writeValueAsString(weeklyDifficultyFrequencyMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble converting weeklyDifficultyFrequencyMap to a JsonString" , e);
        }
    }

    /**
     * Turns an object of type S into an object of type T.
     *
     * @param weeklyDifficultyFrequencyMapJSON to be unconverted
     */
    @Override
    public Map<String, Map<String, Integer>> unconvert(String weeklyDifficultyFrequencyMapJSON) {
        try {
            return objectMapper.readValue(weeklyDifficultyFrequencyMapJSON, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble un-converting weeklyDifficultyFrequencyMapJSON to a HashMap" , e);
        }
    }
}
