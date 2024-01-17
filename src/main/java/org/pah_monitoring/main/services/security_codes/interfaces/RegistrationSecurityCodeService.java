package org.pah_monitoring.main.services.security_codes.interfaces;

import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;

import java.util.UUID;

public interface RegistrationSecurityCodeService {

    boolean isExpired(RegistrationSecurityCode code);

    boolean isNotSuitableForRole(RegistrationSecurityCode code, Role role);

    boolean isNotSuitableForEmail(RegistrationSecurityCode code, String email);

    RegistrationSecurityCode findByUuid(UUID uuid) throws DataSearchingServiceException;

    RegistrationSecurityCode findByStringUuid(String stringUuid) throws UuidUtilsException, DataSearchingServiceException;

    boolean existsByUuid(UUID uuid);

    boolean existsByStringUuid(String stringUuid);

    void deleteByEmail(String email) throws DataSearchingServiceException, DataDeletionServiceException;

}
