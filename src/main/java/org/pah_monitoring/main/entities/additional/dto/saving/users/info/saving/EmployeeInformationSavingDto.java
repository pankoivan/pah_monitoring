package org.pah_monitoring.main.entities.additional.dto.saving.users.info.saving;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeInformationSavingDto {

    @Size(min = 4, max = 128, message = "Минимальная длина должности - 4 символа, максимальная - 128 символов")
    @NotNull(message = "Поле \"post\" не должно отсутствовать")
    @NotEmpty(message = "Должность не должна быть пустой")
    @NotBlank(message = "Должность не должна состоять только из пробельных символов")
    private String post;

}
