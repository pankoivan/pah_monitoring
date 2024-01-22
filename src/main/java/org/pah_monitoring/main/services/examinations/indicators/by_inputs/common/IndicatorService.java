package org.pah_monitoring.main.services.examinations.indicators.by_inputs.common;

import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;

import java.util.List;

public interface IndicatorService<T, M> extends DataAddingValidationService<M> {

    List<T> findAllByPatientId(Integer id) throws DataSearchingServiceException;

    T add(M addingDto);

    void checkAccessRightsForObtainingAllByPatientId(Patient patient) throws NotEnoughRightsServiceException;

    void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException;

}
