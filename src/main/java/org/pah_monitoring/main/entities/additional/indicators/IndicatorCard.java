package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class IndicatorCard {

    private IndicatorType workingName;

    private String name;

    private String filename;

    private String schedule;

    private LocalDateTime date;

    private String postFormLink;

    public String getFormattedDate() {
        return DateTimeFormatConstants.HOUR_MINUTE_SECOND_DAY_MONTH_YEAR.format(date);
    }

}
