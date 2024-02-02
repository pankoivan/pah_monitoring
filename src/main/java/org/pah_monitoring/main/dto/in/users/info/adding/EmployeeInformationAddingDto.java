package org.pah_monitoring.main.dto.in.users.info.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.pah_monitoring.main.dto.in.users.info.saving.EmployeeInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeInformationAddingDto extends EmployeeInformationSavingDto {

    @Valid
    @NotNull(message = "Поле \"userInformationAddingDto\" не должно отсутствовать")
    private UserInformationAddingDto userInformationAddingDto;

}
