package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileIndicatorService<T, M> extends IndicatorService<T, M> {

    Optional<LocalDateTime> getLastExaminationDateFor(AnalysisFile.AnalysisType type, Patient patient);

    Optional<String> getScheduleFor(AnalysisFile.AnalysisType type, Patient patient);

    IndicatorCard getIndicatorCardFor(AnalysisFile.AnalysisType type, Patient patient);

    List<T> findAllByPatientId(AnalysisFile.AnalysisType type, Integer patientId) throws DataSearchingServiceException;

    AnalysisFile add(MultipartFile file, AnalysisFile.AnalysisType analysisType);

}
