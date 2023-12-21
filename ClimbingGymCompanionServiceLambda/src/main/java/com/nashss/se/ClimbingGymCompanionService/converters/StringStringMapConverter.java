package com.nashss.se.ClimbingGymCompanionService.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class StringStringMapConverter implements DynamoDBTypeConverter<String, Map<String, String>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Turns an object of type T into an object of type S.
     *
     * @param StringStringMap to be converted
     */
    @Override
    public String convert(Map<String, String> StringStringMap) {
        try {
            return objectMapper.writeValueAsString(StringStringMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble converting StringStringMap to a JsonString" , e);
        }
    }

    /**
     * Turns an object of type S into an object of type T.
     *
     * @param StringStringMapJsonString to be unconverted
     */
    @Override
    public Map<String, String> unconvert(String StringStringMapJsonString) {
        try {
            return objectMapper.readValue(StringStringMapJsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble un-converting StringStringMapJsonString to a HashMap" , e);
        }
    }
}
