package com.nashss.se.ClimbingGymCompanionService.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.ZonedDateTime;

public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {

    @Override
    public String convert(ZonedDateTime dateTime) {
        return dateTime.toString();
    }

    @Override
    public ZonedDateTime unconvert(String dateTimeRepresentation) {
        return ZonedDateTime.parse(dateTimeRepresentation);
    }
}

