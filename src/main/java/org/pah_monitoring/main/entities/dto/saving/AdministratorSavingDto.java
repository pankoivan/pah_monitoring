package org.pah_monitoring.main.entities.dto.saving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdministratorSavingDto {

    // todo: hibernate validation

    private Integer id;

    private UserSecurityInformationSavingDto userSecurityInformationSavingDto;

    private EmployeeInformationSavingDto employeeInformationSavingDto;

}
