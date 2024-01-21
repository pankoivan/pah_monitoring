package org.pah_monitoring.main.entities.dto.saving.examinations.schedules;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.enums.IndicatorGroup;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationScheduleAddingDto extends ExaminationScheduleSavingDto {

    Integer patientId;

    IndicatorGroup indicatorGroup;

}
