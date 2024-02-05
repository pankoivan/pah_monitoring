package org.pah_monitoring.main.services.main.security_codes.interfaces;

import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

public interface RegistrationSecurityCodeGenerationService<T> extends DataAddingValidationService<T> {

    RegistrationSecurityCode add(T addingDto) throws DataSavingServiceException;

}
