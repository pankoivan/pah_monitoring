package org.pah_monitoring.main.services.validation.interfaces.saving;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface AddingValidationService<T> extends BindingResultMessagesService {

    void checkDataValidityForAdding(T addingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
