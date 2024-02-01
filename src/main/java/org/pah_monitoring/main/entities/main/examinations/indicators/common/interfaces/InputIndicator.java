package org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces;

import org.pah_monitoring.main.entities.main.enums.IndicatorType;

import java.time.LocalDateTime;

public interface InputIndicator extends Indicator {

    IndicatorType getIndicatorGroup();

    LocalDateTime getDate();

}
