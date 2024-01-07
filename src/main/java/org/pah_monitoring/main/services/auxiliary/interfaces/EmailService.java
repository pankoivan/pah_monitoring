package org.pah_monitoring.main.services.auxiliary.interfaces;

import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;

public interface EmailService {

    void sendRegistrationCode(String to, RegistrationSecurityCode code);

}
