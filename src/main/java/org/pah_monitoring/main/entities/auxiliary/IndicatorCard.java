package org.pah_monitoring.main.entities.auxiliary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IndicatorCard {

    private String workingName;

    private String name;

    private String filename;

    private LocalDateTime date;

    public String getFormattedDate() {
        return DateTimeFormatConstants.HOUR_MINUTE_SECOND_DAY_MONTH_YEAR.format(date);
    }

}
