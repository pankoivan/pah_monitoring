package org.pah_monitoring.main.entities.dto.saving.security_codes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.pah_monitoring.main.entities.enums.Role;

@Data
public class RegistrationSecurityCodeByAdminAddingDto {

    @NotNull(message = "Роль не должна отсутствовать")
    Role role;

    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    @NotNull(message = "Почта не должна отсутствовать")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    String email;

    @NotNull(message = "Срок действия не должен отсутствовать")
    ExpirationDate expirationDate;

}
