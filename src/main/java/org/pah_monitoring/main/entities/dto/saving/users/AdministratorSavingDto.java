package org.pah_monitoring.main.entities.dto.saving.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdministratorSavingDto {

    // todo: hibernate validation

    private Integer id;

    private UserSecurityInformationSavingDto userSecurityInformationSavingDto;

    private EmployeeInformationSavingDto employeeInformationSavingDto;

    private String code;

}
