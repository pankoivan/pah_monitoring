package org.pah_monitoring.main.entities.dto.saving.users.info.editing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.EmployeeInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeInformationEditingDto extends EmployeeInformationSavingDto {

    Integer id;

}
