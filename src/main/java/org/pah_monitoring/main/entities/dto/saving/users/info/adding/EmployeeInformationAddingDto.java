package org.pah_monitoring.main.entities.dto.saving.users.info.adding;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.EmployeeInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeInformationAddingDto extends EmployeeInformationSavingDto {

    @Valid
    private UserInformationAddingDto userInformationAddingDto;

}
