package com.nashss.se.ClimbingGymCompanionService.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

    @Override
    public String convert(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public LocalDate unconvert(String dateRepresentation) {
        return LocalDate.parse(dateRepresentation);
    }

}
