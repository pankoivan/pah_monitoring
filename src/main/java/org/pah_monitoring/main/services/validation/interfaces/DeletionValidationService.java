package org.pah_monitoring.main.services.validation.interfaces;

import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;

public interface DeletionValidationService<T> {

    void checkDataValidityForDeleting(T t) throws DataValidationServiceException;

}
