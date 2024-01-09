package org.pah_monitoring.main.services.security_codes.interfaces;

import org.pah_monitoring.main.entities.dto.PageAccessSecurityCodeSavingDto;
import org.pah_monitoring.main.entities.security_codes.PageAccessSecurityCode;

import java.util.UUID;

public interface PageAccessSecurityCodeService {

    PageAccessSecurityCode save(PageAccessSecurityCodeSavingDto code, UUID generatedCode);

    boolean existsByCode(UUID code);

}
