package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class TablesInputIndicatorCard extends InputIndicatorCard {

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
    public boolean isTablesInputIndicatorCard() {
        return true;
    }

    @Override
    public boolean isGraphicsTablesInputIndicatorCard() {
        return false;
    }

}
