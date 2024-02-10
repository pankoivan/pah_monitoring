package org.pah_monitoring.main.dto.out.examinations.schedules;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

@Data
@Builder
public class ExaminationScheduleOutDto implements OutDto {

    private Integer id;

    private Integer patientId;

    private IndicatorType indicatorType;

    private String indicatorTypeAlias;

    private String schedule;

}
