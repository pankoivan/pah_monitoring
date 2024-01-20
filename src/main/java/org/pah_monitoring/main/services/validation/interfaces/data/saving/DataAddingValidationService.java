package org.pah_monitoring.main.services.validation.interfaces.data.saving;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface DataAddingValidationService<T> extends DataValidationBindingResultMessageService {

    void checkDataValidityForAdding(T addingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
