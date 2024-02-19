package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public abstract class IndicatorCard {

    private IndicatorType indicatorType;

    private String name;

    private String filename;

    private String schedule;

    private LocalDateTime date;

    private String postFormLink;

    public String getFormattedDate() {
        return date != null
                ? DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(date)
                : null;
    }

    public abstract boolean isFileIndicatorCard();

    public abstract boolean isInputIndicatorCard();

    public abstract boolean isTableInputIndicatorCard();

    public abstract boolean isGraphicTableInputIndicatorCard();

}
