package org.pah_monitoring.main.services.common.interfaces;

import org.pah_monitoring.auxiliary.exceptions.rest.validation.RestDataSavingValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SavingValidationService<T> {

    void checkBindingResult(BindingResult bindingResult) throws RestDataSavingValidationException;

    void checkValidityForSaving(T t) throws RestDataSavingValidationException;

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
