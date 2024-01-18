package org.pah_monitoring.main.entities.dto.saving.users.info.saving;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSecurityInformationSavingDto {

    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    private String email;

    @Size(min = 3, max = 63, message = "Минимальная длина пароля - 3 символа, максимальная - 63 символа")
    @NotEmpty(message = "Пароль не должен быть пустым")
    @NotBlank(message = "Пароль не должен состоять только из пробельных символов")
    private String password;

}
