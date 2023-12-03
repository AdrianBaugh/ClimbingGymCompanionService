package com.nashss.se.ClimbingGymCompanionService.converters;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZonedDateTimeConverterTest {
    ZonedDateTimeConverter converter = new ZonedDateTimeConverter();

    @Test
    void convert_withProperZonedDateTime_returnsProperString() {
        // Given
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        // When
        String converted = converter.convert(zonedDateTime);
        String expected = zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        // Then
        assertEquals(expected, converted);
    }

    @Test
    void unconvert_withProperString_returnsProperZonedDateTime() {
        // Given
        String dateTimeString = "2023-09-01T12:00:00-05:00[America/Chicago]";

        // When
        ZonedDateTime result = converter.unconvert(dateTimeString);
        ZonedDateTime expected = ZonedDateTime.of(
                2023, 9, 1,
                12, 0, 0, 0,
                ZoneId.of("America/Chicago"));

        // Then
        assertEquals(expected, result);
    }
}
