package org.pah_monitoring.main.entities.dto.saving.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorSavingDto {

    private Integer id;

    @Size(min = 3, max = 128, message = "Минимальная длина названия вуза - 3 символа, максимальная - 128 символов")
    @NotEmpty(message = "Название вуза не должно быть пустым")
    @NotBlank(message = "Название вуза не должно состоять только из пробельных символов")
    private String university;

    @Valid
    private UserSecurityInformationSavingDto userSecurityInformationSavingDto;

    @Valid
    private EmployeeInformationSavingDto employeeInformationSavingDto;

    private String code;

}
