package dev.ilong299.logger.datetime;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeFormatter {
    public static String formatDateAndTime(String input) {
        // Init one time for do not call LocalDateTime#now each time it used
        LocalDateTime now = LocalDateTime.now();

        return input
                .replace("{YEAR}", String.valueOf(now.getYear()))
                .replace("{MONTH}", String.format("%02d", now.getMonthValue()))
                .replace("{MONTH_NAME}", now.getMonth().getDisplayName(TextStyle.FULL, Locale.ROOT))
                .replace("{DAY}", String.format("%02d", now.getDayOfMonth()))
                .replace("{HOUR}", String.format("%02d", now.getHour()))
                .replace("{MINUTE}", String.format("%02d", now.getMinute()))
                .replace("{SECOND}", String.format("%02d", now.getSecond()));
    }
}
