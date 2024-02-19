package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class FileIndicatorCard extends IndicatorCard {

    private String fileLink;

    @Override
    public boolean isFileIndicatorCard() {
        return true;
    }

    @Override
    public boolean isInputIndicatorCard() {
        return false;
    }

    @Override
    public boolean isTableInputIndicatorCard() {
        return false;
    }

    @Override
    public boolean isGraphicTableInputIndicatorCard() {
        return false;
    }

}
