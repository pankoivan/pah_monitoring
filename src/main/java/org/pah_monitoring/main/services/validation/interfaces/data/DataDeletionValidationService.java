package org.pah_monitoring.main.services.validation.interfaces.data;

import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;

public interface DataDeletionValidationService<T> {

    void checkDataValidityForDeleting(T entity) throws DataValidationServiceException;

}
