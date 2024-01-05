package com.nashss.se.ClimbingGymCompanionService.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class StringIntegerMapConverter implements DynamoDBTypeConverter<String, Map<String, Integer>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Turns an object of type T into an object of type S.
     *
     * @param stringIntegerMap to be converted
     */
    @Override
    public String convert(Map<String, Integer> stringIntegerMap) {
        try {
            return objectMapper.writeValueAsString(stringIntegerMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble converting stringIntegerMap to a JsonString" , e);

        }
    }

    /**
     * Turns an object of type S into an object of type T.
     *
     * @param stringIntegerMapJsonString to be unconverted
     */
    @Override
    public Map<String, Integer> unconvert(String stringIntegerMapJsonString) {
        try {
            return objectMapper.readValue(stringIntegerMapJsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble un-converting stringIntegerMapJsonString to a HashMap" , e);
        }
    }
}
