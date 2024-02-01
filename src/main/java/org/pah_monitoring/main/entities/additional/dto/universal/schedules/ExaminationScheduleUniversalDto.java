package org.pah_monitoring.main.entities.additional.dto.universal.schedules;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;

@Data
public class ExaminationScheduleUniversalDto {

    @NotNull(message = "Поле \"patientId\" не должно отсутствовать")
    Integer patientId;

    @NotNull(message = "Поле \"indicatorType\" не должно отсутствовать")
    IndicatorType indicatorType;

    @NotNull(message = "Поле \"schedule\" не должно отсутствовать")
    @NotEmpty(message = "Расписание не должно быть пустым")
    @NotBlank(message = "Расписание не должно состоять только из пробельных символов")
    @Size(min = 12, max = 512, message = "Минимальная длина расписания - 7 символов, максимальная - 24 символа")
    String schedule;

}
