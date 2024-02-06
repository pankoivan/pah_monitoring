package org.pah_monitoring.main.dto.in.users.info.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserSecurityInformationAddingDto extends UserSecurityInformationSavingDto {

    @NotNull(message = "Поле \"password\" не должно отсутствовать")
    @Size(min = 3, max = 63, message = "Минимальная длина пароля - 3 символа, максимальная - 63 символа")
    private String password;

}
