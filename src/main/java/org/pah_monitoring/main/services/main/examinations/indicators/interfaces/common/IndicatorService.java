package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

public interface IndicatorService<T, M> extends DataAddingValidationService<M> {

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException;

    void checkAccessRightsForObtainingAll(Patient patient) throws NotEnoughRightsServiceException;

}
