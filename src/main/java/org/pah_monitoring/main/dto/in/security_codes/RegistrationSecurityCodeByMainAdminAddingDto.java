package org.pah_monitoring.main.dto.in.security_codes;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.ExpirationDate;

@Data
public class RegistrationSecurityCodeByMainAdminAddingDto {

    @NotNull(message = "Поле \"hospitalRegistrationRequestId\" не должно отсутствовать")
    private Integer hospitalRegistrationRequestId;

    @NotNull(message = "Поле \"expirationDate\" не должно отсутствовать")
    private ExpirationDate expirationDate;

}
