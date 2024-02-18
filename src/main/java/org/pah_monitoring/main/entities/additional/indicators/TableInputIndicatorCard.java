package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class TableInputIndicatorCard extends InputIndicatorCard {

    private String tablesLink;

    @Override
    public boolean isFileIndicatorCard() {
        return false;
    }

    @Override
    public boolean isInputIndicatorCard() {
        return true;
    }

    @Override
    public boolean isTableInputIndicatorCard() {
        return true;
    }

    @Override
    public boolean isGraphicTableInputIndicatorCard() {
        return false;
    }

}
