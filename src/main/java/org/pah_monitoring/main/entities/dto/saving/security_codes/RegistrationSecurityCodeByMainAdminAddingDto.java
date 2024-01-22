package org.pah_monitoring.main.entities.dto.saving.security_codes;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.enums.ExpirationDate;

@Data
public class RegistrationSecurityCodeByMainAdminAddingDto {

    @NotNull(message = "Поле \"hospitalRegistrationRequestId\" не должно отсутствовать")
    Integer hospitalRegistrationRequestId;

    @NotNull(message = "Поле \"expirationDate\" не должно отсутствовать")
    ExpirationDate expirationDate;

}
