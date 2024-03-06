package org.pah_monitoring.main.dto.in.examinations.schedules;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class ExaminationScheduleSavingDto {

    @NotNull(message = "Поле \"schedule\" не должно отсутствовать")
    @NotEmpty(message = "Расписание не должно быть пустым")
    @NotBlank(message = "Расписание не должно состоять только из пробельных символов")
    @Size(min = 5, max = 32, message = "Минимальная длина расписания - 5 символов, максимальная - 32 символа")
    private String schedule;

}
