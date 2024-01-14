package org.pah_monitoring.main.entities.dto.saving.users.info;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeInformationSavingDto {

    private Integer id;

    @Size(min = 4, max = 128, message = "Минимальная длина должности - 4 символа, максимальная - 128 символов")
    @NotEmpty(message = "Должность не должна быть пустой")
    @NotBlank(message = "Должность не должна состоять только из пробельных символов")
    private String post;

    @Valid
    private UserInformationSavingDto userInformationSavingDto;

}
