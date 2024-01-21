package org.pah_monitoring.main.entities.dto.saving.security_codes;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.enums.ExpirationDate;

@Data
public class RegistrationSecurityCodeByMainAdminAddingDto {

    @NotNull(message = "Идентификатор запроса не должен отсутствовать")
    Integer hospitalRegistrationRequestId;

    @NotNull(message = "Срок действия не должен отсутствовать")
    ExpirationDate expirationDate;

}
