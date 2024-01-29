package org.pah_monitoring.auxiliary.constants;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public final class DateTimeFormatConstants {

    public static final DateTimeFormatter DAY_MONTH_YEAR =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final DateTimeFormatter HOUR_MINUTE_SECOND_DAY_MONTH_YEAR =
            DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    public static final DateTimeFormatter DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND =
            DateTimeFormatter.ofPattern("dd-MM-yyyy в HH:mm:ss");

    public static final DateTimeFormatter DAY_MONTH_YEAR_WHITESPACE_HOUR_MINUTE_SECOND =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

}
