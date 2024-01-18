package org.pah_monitoring.main.services.security_codes.interfaces;

import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface RegistrationSecurityCodeGenerationService<T> extends SavingValidationService<T> {

    RegistrationSecurityCode add(T t) throws DataSavingServiceException;

}
