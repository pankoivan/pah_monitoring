package org.pah_monitoring.main.services.main.validation.interfaces.data.deletion;

import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;

public interface DataDeletionValidationService<T> {

    void checkDataValidityForDeletion(T entity) throws DataValidationServiceException;

}
