package utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormat {

    // Define the common date formatter for dd-MM-yyyy format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final SimpleDateFormat LEGACY_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat LEGACY_DATE_TIME_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    /**
     * Format a LocalDate to dd-MM-yyyy format.
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Format a LocalDateTime to dd-MM-yyyy HH:mm format.
     */
    public static String formatWithTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    /**
     * Format a legacy Date (java.util.Date) to dd-MM-yyyy format.
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        return LEGACY_DATE_FORMATTER.format(date);
    }

    /**
     * Format a legacy Date (java.util.Date) to dd-MM-yyyy HH:mm format.
     */
    public static String formatWithTime(Date date) {
        if (date == null) {
            return "";
        }
        return LEGACY_DATE_TIME_FORMATTER.format(date);
    }

    /**
     * Format a LocalDateTime or LocalDate to dd-MM-yyyy without time.
     * Automatically extracts the date part if input is LocalDateTime.
     */
    public static String formatNoTime(Object date) {
        if (date == null) {
            return "";
        }
        if (date instanceof LocalDate) {
            return format((LocalDate) date);
        }
        if (date instanceof LocalDateTime) {
            return format(((LocalDateTime) date).toLocalDate());
        }
        throw new IllegalArgumentException("Unsupported date type: " + date.getClass().getName());
    }

    /**
     * Parse a String date input and format it to dd-MM-yyyy format.
     */
    public static String format(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("[d-M-yyyy][dd-MM-yyyy]"));
            return DATE_FORMATTER.format(date);
        } catch (Exception e) {
            System.err.println("Invalid date format: " + dateStr);
            return dateStr;
        }
    }

    /**
     * Parse a String date input and format it to dd-MM-yyyy HH:mm format.
     */
    public static String formatWithTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("[d-M-yyyy HH:mm][dd-MM-yyyy HH:mm]"));
            return DATE_TIME_FORMATTER.format(dateTime);
        } catch (Exception e) {
            System.err.println("Invalid date-time format: " + dateStr);
            return dateStr;
        }
    }

    /**
     * General format method that handles LocalDate, LocalDateTime, and Date.
     */
    public static String format(Object date) {
        if (date == null) {
            return "";
        }
        if (date instanceof LocalDate) {
            return format((LocalDate) date);
        }
        if (date instanceof LocalDateTime) {
            return formatWithTime((LocalDateTime) date);
        }
        if (date instanceof Date) {
            return formatWithTime((Date) date);
        }
        throw new IllegalArgumentException("Unsupported date type: " + date.getClass().getName());
    }
}
