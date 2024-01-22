package org.pah_monitoring.main.entities.examinations.indicators.common.interfaces;

import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.enums.IndicatorGroup;
import org.pah_monitoring.main.entities.examinations.examinations.Examination;

public interface Indicator extends BaseEntity {

    IndicatorGroup getIndicatorGroup();

    Examination getExamination();

}
