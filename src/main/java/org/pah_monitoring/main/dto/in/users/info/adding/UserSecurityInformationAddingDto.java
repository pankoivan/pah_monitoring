package org.pah_monitoring.main.dto.in.users.info.adding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.dto.in.users.info.saving.UserSecurityInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSecurityInformationAddingDto extends UserSecurityInformationSavingDto {

    @Size(min = 3, max = 63, message = "Минимальная длина пароля - 3 символа, максимальная - 63 символа")
    @NotNull(message = "Поле \"password\" не должно отсутствовать")
    private String password;

}
