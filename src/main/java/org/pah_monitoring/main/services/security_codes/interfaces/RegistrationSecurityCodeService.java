package org.pah_monitoring.main.services.security_codes.interfaces;

import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;

import java.util.UUID;

public interface RegistrationSecurityCodeService {

    boolean existsByStringCode(String stringCode);

    RegistrationSecurityCode findByCode(UUID code) throws DataSearchingServiceException;

}
