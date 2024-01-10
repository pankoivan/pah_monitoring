package org.pah_monitoring.main.services.common.interfaces;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SavingValidationService<T> {

    boolean isNotValidForSaving(T t, BindingResult bindingResult);

    default String bindingResultAnyErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findAny()
                .orElse("");
    }

    default List<String> bindingResultAllErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

}
