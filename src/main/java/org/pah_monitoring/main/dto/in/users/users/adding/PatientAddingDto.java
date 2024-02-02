package org.pah_monitoring.main.dto.in.users.users.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.dto.in.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.dto.in.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.adding.UserSecurityInformationAddingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientAddingDto extends PatientSavingDto implements HospitalUserAddingInfo {

    @Valid
    @NotNull(message = "Поле \"userSecurityInformationAddingDto\" не должно отсутствовать")
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    @NotNull(message = "Поле \"userInformationAddingDto\" не должно отсутствовать")
    private UserInformationAddingDto userInformationAddingDto;

    @NotNull(message = "Поле \"code\" не должно отсутствовать")
    private String code;

}
