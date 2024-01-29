package org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.common;

import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IndicatorService<T, M, N, R> extends DataAddingValidationService<M> {

    List<T> findAllByPatientId(Integer id) throws DataSearchingServiceException;

    Optional<LocalDateTime> getLastExaminationDateFor(Patient patient);

    List<N> forTables(List<T> list);

    List<R> forGraphics(List<T> list);

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessRightsForObtainingAllByPatientId(Patient patient) throws NotEnoughRightsServiceException;

    void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException;

}
