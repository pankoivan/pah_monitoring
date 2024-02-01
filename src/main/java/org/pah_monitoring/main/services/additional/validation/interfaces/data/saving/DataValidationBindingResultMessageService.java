package org.pah_monitoring.main.services.additional.validation.interfaces.data.saving;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface DataValidationBindingResultMessageService {

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
