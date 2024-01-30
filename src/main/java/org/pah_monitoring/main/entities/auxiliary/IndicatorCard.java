package org.pah_monitoring.main.entities.auxiliary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class IndicatorCard {

    private String workingName;

    private String name;

    private String filename;

    private String schedule;

    private LocalDateTime date;

    public String getFormattedDate() {
        return DateTimeFormatConstants.HOUR_MINUTE_SECOND_DAY_MONTH_YEAR.format(date);
    }

}
