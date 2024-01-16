package org.pah_monitoring.main.entities.dto.saving.security_codes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.enums.ExpirationDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationSecurityCodeByMainAdminSavingDto {

    Integer hospitalRegistrationRequestId;

    ExpirationDate expirationDate;

}
