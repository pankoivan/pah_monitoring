package org.pah_monitoring.main.entities.dto.saving.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientSavingDto {

    private Integer id;

    @Valid
    private UserSecurityInformationSavingDto userSecurityInformationSavingDto;

    @Valid
    private UserInformationSavingDto userInformationSavingDto;

    private String code;

}
