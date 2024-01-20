package org.pah_monitoring.main.services.validation.interfaces.saving;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface BindingResultMessagesService {

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
