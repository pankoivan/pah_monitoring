package org.pah_monitoring.main.services.validation.interfaces.saving;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface EditingValidationService<T> extends BindingResultMessagesService {

    void checkDataValidityForEditing(T editingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
