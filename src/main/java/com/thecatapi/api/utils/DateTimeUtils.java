package com.thecatapi.api.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

@UtilityClass
public class DateTimeUtils {

    public static LocalDateTime stringToLocalDateTime(String dateStr, DateTimeFormatter dtf) {
        return LocalDateTime.parse(dateStr, dtf);
    }

    public static LocalDateTime getCurrentTimeInUTC() {
        return ZonedDateTime
                .now(UTC)
                .toLocalDateTime();
    }
}
