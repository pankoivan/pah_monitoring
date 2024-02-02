package org.pah_monitoring.main.services.main.security_codes.interfaces;

import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataSavingValidationService;

public interface RegistrationSecurityCodeGenerationService<T> extends DataSavingValidationService<T> {

    RegistrationSecurityCode add(T t) throws DataSavingServiceException;

}
