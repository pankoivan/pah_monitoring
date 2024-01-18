package org.pah_monitoring.main.entities.dto.saving.users.users.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorAddingDto {

    @Size(min = 3, max = 128, message = "Минимальная длина названия вуза - 3 символа, максимальная - 128 символов")
    @NotEmpty(message = "Название вуза не должно быть пустым")
    @NotBlank(message = "Название вуза не должно состоять только из пробельных символов")
    private String university;

    @Valid
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    private EmployeeInformationAddingDto employeeInformationAddingDto;

    private String code;

}
