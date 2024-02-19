package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.experimental.SuperBuilder;
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

    private String formattedDate;

    private String postFormLink;

    public abstract boolean isFileIndicatorCard();

    public abstract boolean isInputIndicatorCard();

    public abstract boolean isTableInputIndicatorCard();

    public abstract boolean isGraphicTableInputIndicatorCard();

}
