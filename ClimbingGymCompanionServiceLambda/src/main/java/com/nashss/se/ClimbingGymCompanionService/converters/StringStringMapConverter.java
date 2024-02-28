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
     * @param stringStringMap to be converted
     */
    @Override
    public String convert(Map<String, String> stringStringMap) {
        try {
            return objectMapper.writeValueAsString(stringStringMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble converting stringStringMap to a JsonString" , e);
        }
    }

    /**
     * Turns an object of type S into an object of type T.
     *
     * @param stringStringMapJsonString to be unconverted
     */
    @Override
    public Map<String, String> unconvert(String stringStringMapJsonString) {
        try {
            return objectMapper.readValue(stringStringMapJsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble un-converting stringStringMapJsonString to a HashMap" , e);
        }
    }
}
