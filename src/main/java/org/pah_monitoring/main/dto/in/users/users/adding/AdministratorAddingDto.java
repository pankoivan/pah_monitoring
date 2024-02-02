package org.pah_monitoring.main.dto.in.users.users.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.pah_monitoring.main.dto.in.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.dto.in.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.dto.in.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.adding.UserSecurityInformationAddingDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdministratorAddingDto extends AdministratorSavingDto implements HospitalUserAddingInfo {

    @Valid
    @NotNull(message = "Поле \"userSecurityInformationAddingDto\" не должно отсутствовать")
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    @NotNull(message = "Поле \"employeeInformationAddingDto\" не должно отсутствовать")
    private EmployeeInformationAddingDto employeeInformationAddingDto;

    @NotNull(message = "Поле \"code\" не должно отсутствовать")
    private String code;

}
