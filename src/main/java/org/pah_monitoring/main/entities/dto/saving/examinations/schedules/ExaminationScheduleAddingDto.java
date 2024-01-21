package org.pah_monitoring.main.entities.dto.saving.examinations.schedules;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.enums.IndicatorGroup;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationScheduleAddingDto extends ExaminationScheduleSavingDto {

    @NotNull(message = "Идентификатор пациента не должен отсутствовать")
    Integer patientId;

    @NotNull(message = "Группа показателей не должна отсутствовать")
    IndicatorGroup indicatorGroup;

}
