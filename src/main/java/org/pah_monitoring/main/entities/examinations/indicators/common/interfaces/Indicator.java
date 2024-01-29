package org.pah_monitoring.main.entities.examinations.indicators.common.interfaces;

import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.enums.IndicatorType;

public interface Indicator extends BaseEntity {

    IndicatorType getIndicatorGroup();

}
