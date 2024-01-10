package com.jar.kiranaregister.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.jar.kiranaregister.exception.InvalidDateException;

/**
 * Utility class for date-related operations.
 */
public class DateUtil {

    // Use ISO_DATE format for parsing dates
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

    /**
     * Parses a string representing a date into a LocalDate object.
     *
     * @param dateString The string representing the date.
     * @return The LocalDate object parsed from the string.
     * @throws InvalidDateException If the provided date string is invalid or does not match the expected format.
     */
    public static LocalDate stringToDate(String dateString) throws InvalidDateException {
        LocalDate date = null;

        try {
            date = LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            // Throw an InvalidDateException with a meaningful error message
            throw new InvalidDateException(String.format("The provided date '%s' is invalid or does not match the expected format. Please ensure that the date is valid and follows the format '%s'.", dateString, dateFormatter.format(LocalDate.now())));
        }

        return date;
    }
}
