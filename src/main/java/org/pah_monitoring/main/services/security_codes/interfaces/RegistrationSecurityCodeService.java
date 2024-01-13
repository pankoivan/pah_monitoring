package org.pah_monitoring.main.services.security_codes.interfaces;

public interface RegistrationSecurityCodeService {

    boolean existsByStringCode(String stringCode);

}
