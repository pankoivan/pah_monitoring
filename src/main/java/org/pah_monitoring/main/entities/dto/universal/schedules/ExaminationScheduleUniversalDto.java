package org.pah_monitoring.main.entities.dto.universal.schedules;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.enums.IndicatorType;

@Data
public class ExaminationScheduleUniversalDto {

    @NotNull(message = "Поле \"patientId\" не должно отсутствовать")
    Integer patientId;

    @NotNull(message = "Поле \"indicatorGroup\" не должно отсутствовать")
    IndicatorType indicatorType;

}
