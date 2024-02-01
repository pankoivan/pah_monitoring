package org.pah_monitoring.main.entities.additional.dto.saving.users.users.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.additional.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.additional.dto.saving.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.additional.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.additional.dto.saving.users.info.adding.UserSecurityInformationAddingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorAddingDto extends DoctorSavingDto implements HospitalUserAddingInfo {

    @Valid
    @NotNull(message = "Поле \"userSecurityInformationAddingDto\" не должно отсутствовать")
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    @NotNull(message = "Поле \"employeeInformationAddingDto\" не должно отсутствовать")
    private EmployeeInformationAddingDto employeeInformationAddingDto;

    @NotNull(message = "Поле \"code\" не должно отсутствовать")
    private String code;

}
