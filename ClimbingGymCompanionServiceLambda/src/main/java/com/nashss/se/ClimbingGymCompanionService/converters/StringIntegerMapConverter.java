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
     * @param StringIntegerMap to be converted
     */
    @Override
    public String convert(Map<String, Integer> StringIntegerMap) {
        try {
            return objectMapper.writeValueAsString(StringIntegerMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble converting StringIntegerMap to a JsonString" , e);

        }
    }

    /**
     * Turns an object of type S into an object of type T.
     *
     * @param StringIntegerMapJsonString to be unconverted
     */
    @Override
    public Map<String, Integer> unconvert(String StringIntegerMapJsonString) {
        try {
            return objectMapper.readValue(StringIntegerMapJsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble un-converting StringIntegerMapJsonString to a HashMap" , e);
        }
    }
}
