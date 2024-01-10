package org.pah_monitoring.main.services.common.interfaces;

import org.pah_monitoring.auxiliary.exceptions.rest.validation.RestDataDeletionValidationException;

public interface DeletionValidationService<T> {

    void isValidForDeletion(T t) throws RestDataDeletionValidationException;

}
