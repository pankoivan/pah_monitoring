package org.pah_monitoring.main.dto.in.security_codes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.ExpirationDate;
import org.pah_monitoring.main.entities.main.enums.Role;

@Data
public class RegistrationSecurityCodeByAdminAddingDto {

    @NotNull(message = "Поле \"role\" не должно отсутствовать")
    private Role role;

    @NotNull(message = "Поле \"email\" не должно отсутствовать")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    private String email;

    @NotNull(message = "Поле \"expirationDate\" не должно отсутствовать")
    private ExpirationDate expirationDate;

}
