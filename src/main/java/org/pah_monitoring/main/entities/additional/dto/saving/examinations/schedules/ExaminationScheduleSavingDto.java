package org.pah_monitoring.main.entities.additional.dto.saving.examinations.schedules;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExaminationScheduleSavingDto {

    @Size(min = 5, max = 24, message = "Минимальная длина расписания - 5 символов, максимальная - 24 символа")
    @NotNull(message = "Поле \"schedule\" не должно отсутствовать")
    @NotEmpty(message = "Расписание не должно быть пустым")
    @NotBlank(message = "Расписание не должно состоять только из пробельных символов")
    private String schedule;

}
