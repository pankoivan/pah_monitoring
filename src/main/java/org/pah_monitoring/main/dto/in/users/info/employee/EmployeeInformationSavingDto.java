package org.pah_monitoring.main.dto.in.users.info.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class EmployeeInformationSavingDto {

    @NotNull(message = "Поле \"post\" не должно отсутствовать")
    @NotEmpty(message = "Должность не должна быть пустой")
    @NotBlank(message = "Должность не должна состоять только из пробельных символов")
    @Size(min = 4, max = 128, message = "Минимальная длина должности - 4 символа, максимальная - 128 символов")
    private String post;

}
