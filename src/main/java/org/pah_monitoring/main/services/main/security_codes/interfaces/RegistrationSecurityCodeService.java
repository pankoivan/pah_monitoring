package org.pah_monitoring.main.services.main.security_codes.interfaces;

import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;

import java.util.UUID;

public interface RegistrationSecurityCodeService {

    boolean existsByEmail(String email);

    boolean existsByUuid(UUID uuid);

    boolean existsByStringUuid(String stringUuid);

    RegistrationSecurityCode findByUuid(UUID uuid) throws DataSearchingServiceException;

    RegistrationSecurityCode findByStringUuid(String stringUuid) throws UuidUtilsException, DataSearchingServiceException;

    void deleteByEmail(String email) throws DataSearchingServiceException, DataDeletionServiceException;

    void deleteExpired();

}
