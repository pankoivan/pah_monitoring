package org.pah_monitoring.main.entities.dto.saving.users.users.adding;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorAddingDto extends DoctorSavingDto  implements HospitalUserAddingInfo {

    @Valid
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    private EmployeeInformationAddingDto employeeInformationAddingDto;

    private String code;

}
