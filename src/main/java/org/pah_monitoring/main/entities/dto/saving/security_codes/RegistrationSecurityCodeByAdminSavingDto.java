package org.pah_monitoring.main.entities.dto.saving.security_codes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.pah_monitoring.main.entities.enums.Role;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationSecurityCodeByAdminSavingDto {

    Role role;

    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    String email;

    ExpirationDate expirationDate;

}
