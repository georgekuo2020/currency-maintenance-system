package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class DateUtil {

    /**
     * Parses an ISO 8601 formatted date-time string and converts it to a LocalDateTime object.
     *
     * @param isoDateTime the ISO 8601 formatted date-time string
     * @return the LocalDateTime object
     */
    public static LocalDateTime parseISO8601Time2LocalDateTime(String isoDateTime) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(isoDateTime);
        return offsetDateTime.toLocalDateTime();
    }
}
