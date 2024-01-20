package org.pah_monitoring.main.services.validation.interfaces.data.saving;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface DataEditingValidationService<T> extends DataValidationBindingResultMessageService {

    void checkDataValidityForEditing(T editingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
