package org.pah_monitoring.main.entities.dto.saving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeInformationSavingDto {

    // todo: hibernate validation

    private Integer id;

    private String post;

    private UserInformationSavingDto userInformationSavingDto;

}
