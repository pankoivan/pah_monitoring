package org.pah_monitoring.main.services.examinations.indicators.interfaces;

import org.pah_monitoring.main.entities.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.examinations.indicators.interfaces.common.IndicatorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnalysisFileService<T, M> extends IndicatorService<T, M> {

    List<T> findAllByPatientId(AnalysisFile.AnalysisType type, Integer id) throws DataSearchingServiceException;

    Optional<LocalDateTime> getLastExaminationDateFor(AnalysisFile.AnalysisType type, Patient patient);

}
