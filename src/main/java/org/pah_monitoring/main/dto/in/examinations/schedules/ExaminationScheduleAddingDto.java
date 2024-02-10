package org.pah_monitoring.main.dto.in.examinations.schedules;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExaminationScheduleAddingDto extends ExaminationScheduleSavingDto {

    @NotNull(message = "Поле \"patientId\" не должно отсутствовать")
    private Integer patientId;

    @NotNull(message = "Поле \"indicatorType\" не должно отсутствовать")
    private IndicatorType indicatorType;

}
