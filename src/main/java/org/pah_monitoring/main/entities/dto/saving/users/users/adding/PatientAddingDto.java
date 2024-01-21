package org.pah_monitoring.main.entities.dto.saving.users.users.adding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientAddingDto extends PatientSavingDto implements HospitalUserAddingInfo {

    @Valid
    @NotNull(message = "Логин-информация не должна отсутствовать")
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    @NotNull(message = "Общая информация не должна отсутствовать")
    private UserInformationAddingDto userInformationAddingDto;

    @NotNull(message = "Код не должен отсутствовать")
    private String code;

}
