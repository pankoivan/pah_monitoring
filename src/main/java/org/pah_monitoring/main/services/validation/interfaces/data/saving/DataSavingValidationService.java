package org.pah_monitoring.main.services.validation.interfaces.data.saving;

import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface DataSavingValidationService<T> extends DataValidationBindingResultMessageService {

    void checkDataValidityForSaving(T savingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
