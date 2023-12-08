package com.nashss.se.ClimbingGymCompanionService.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class BetaMapConverter implements DynamoDBTypeConverter<String, Map<String, String>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Turns an object of type T into an object of type S.
     *
     * @param betaMap to be converted
     */
    @Override
    public String convert(Map<String, String> betaMap) {
        try {
            return objectMapper.writeValueAsString(betaMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble converting betaMap to a JsonString" , e);

        }
    }

    /**
     * Turns an object of type S into an object of type T.
     *
     * @param betaMapJsonString to be unconverted
     */
    @Override
    public Map<String, String> unconvert(String betaMapJsonString) {
        try {
            return objectMapper.readValue(betaMapJsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Trouble un-converting betaMapJsonString to a HashMap" , e);
        }
    }
}
