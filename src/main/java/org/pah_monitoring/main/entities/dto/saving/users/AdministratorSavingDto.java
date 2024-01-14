package org.pah_monitoring.main.entities.dto.saving.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdministratorSavingDto {

    private Integer id;

    @Valid
    private UserSecurityInformationSavingDto userSecurityInformationSavingDto;

    @Valid
    private EmployeeInformationSavingDto employeeInformationSavingDto;

    private String code;

}
