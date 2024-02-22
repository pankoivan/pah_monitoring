package org.pah_monitoring.main.services.main.security_codes.interfaces;

import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

public interface RegistrationSecurityCodeGenerationService<T> extends DataAddingValidationService<T> {

    RegistrationSecurityCode generate(T addingDto) throws DataSavingServiceException;

    RegistrationSecurityCode generateAndSend(T addingDto) throws DataSavingServiceException, EmailSendingException;

}
