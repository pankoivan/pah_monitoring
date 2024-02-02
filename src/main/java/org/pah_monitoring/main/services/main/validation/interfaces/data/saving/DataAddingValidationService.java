package org.pah_monitoring.main.services.main.validation.interfaces.data.saving;

import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface DataAddingValidationService<T> extends DataValidationBindingResultMessageService {

    void checkDataValidityForAdding(T addingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
