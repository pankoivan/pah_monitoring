package org.pah_monitoring.main.entities.dto.saving.examinations.schedules;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationScheduleEditingDto extends ExaminationScheduleSavingDto {

    Integer id;

}
