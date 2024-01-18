package org.pah_monitoring.main.entities.dto.saving.users.users.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorAddingDto extends DoctorSavingDto {

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
