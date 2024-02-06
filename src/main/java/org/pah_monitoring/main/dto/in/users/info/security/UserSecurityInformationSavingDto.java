package org.pah_monitoring.main.dto.in.users.info.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class UserSecurityInformationSavingDto {

    @NotNull(message = "Поле \"email\" не должно отсутствовать")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    private String email;

}
