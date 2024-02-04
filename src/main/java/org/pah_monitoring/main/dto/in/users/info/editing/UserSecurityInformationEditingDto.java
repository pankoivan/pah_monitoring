package org.pah_monitoring.main.dto.in.users.info.editing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.dto.in.users.info.saving.UserSecurityInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSecurityInformationEditingDto extends UserSecurityInformationSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    Integer id;

    private String password;

}
