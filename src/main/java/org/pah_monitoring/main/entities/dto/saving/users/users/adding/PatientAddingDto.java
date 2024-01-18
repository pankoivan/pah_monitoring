package org.pah_monitoring.main.entities.dto.saving.users.users.adding;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientAddingDto extends PatientSavingDto {

    @Valid
    private UserSecurityInformationAddingDto userSecurityInformationAddingDto;

    @Valid
    private UserInformationAddingDto userInformationAddingDto;

    private String code;

}
