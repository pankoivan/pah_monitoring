package org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces;

import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

import java.time.LocalDateTime;

public interface Indicator extends BaseEntity {

    IndicatorType getIndicatorType();

    LocalDateTime getDate();

}
