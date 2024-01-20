package org.pah_monitoring.main.services.validation.interfaces.saving;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface SavingValidationService<T> extends BindingResultMessagesService {

    void checkDataValidityForSaving(T savingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
