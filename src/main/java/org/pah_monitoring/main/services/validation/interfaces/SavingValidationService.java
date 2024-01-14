package org.pah_monitoring.main.services.validation.interfaces;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface SavingValidationService<T> {

    void checkDataValidityForSaving(T t, BindingResult bindingResult) throws DataValidationServiceException;

    default String bindingResultAnyErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .findAny()
                .orElse("");
    }

    default List<String> bindingResultAllErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }

}
