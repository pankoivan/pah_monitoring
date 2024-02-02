package org.pah_monitoring.main.dto.in.examinations.schedules;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationScheduleAddingDto extends ExaminationScheduleSavingDto {

    @NotNull(message = "Поле \"patientId\" не должно отсутствовать")
    Integer patientId;

    @NotNull(message = "Поле \"indicatorGroup\" не должно отсутствовать")
    IndicatorType indicatorType;

}
