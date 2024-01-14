package org.pah_monitoring.main.entities.dto.saving.users.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSecurityInformationSavingDto {

    // todo: hibernate validation

    private Integer id;

    private String email;

    private String password;

}
