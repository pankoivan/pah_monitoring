package org.pah_monitoring.main.services.common.interfaces;

import org.pah_monitoring.auxiliary.exceptions.rest.validation.RestDataSavingValidationException;

public interface SavingValidationService<T> {

    void isValidForSaving(T t) throws RestDataSavingValidationException;

}
