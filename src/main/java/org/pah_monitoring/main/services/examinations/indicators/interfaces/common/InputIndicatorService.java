package org.pah_monitoring.main.services.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InputIndicatorService<T, M, N, R> extends IndicatorService<T, M> {

    List<T> findAllByPatientId(Integer id) throws DataSearchingServiceException;

    Optional<LocalDateTime> getLastExaminationDateFor(Patient patient);

    List<N> forTables(List<T> list);

    List<R> forGraphics(List<T> list);

}
