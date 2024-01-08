package org.pah_monitoring.main.services.security_codes.interfaces;

import org.pah_monitoring.main.entities.other.Hospital;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;

import java.util.UUID;

public interface RegistrationSecurityCodeService {

    void save(RegistrationSecurityCode code, UUID generatedCode, Hospital hospital);

    boolean existsByCode(UUID code);

}
