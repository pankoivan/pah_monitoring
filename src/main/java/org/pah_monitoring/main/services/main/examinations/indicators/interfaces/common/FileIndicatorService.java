package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileIndicatorService<T> extends IndicatorService<T> {

    Optional<LocalDateTime> getLastExaminationDateFor(AnalysisFile.AnalysisType type, Patient patient);

    Optional<String> getScheduleFor(AnalysisFile.AnalysisType type, Patient patient);

    IndicatorCard getIndicatorCardFor(AnalysisFile.AnalysisType type, Patient patient);

    List<T> findAllByPatientId(AnalysisFile.AnalysisType type, Integer patientId) throws DataSearchingServiceException;

    T add(MultipartFile file, AnalysisFile.AnalysisType analysisType) throws DataSavingServiceException;

    void checkDataValidityForAdding(MultipartFile file) throws DataValidationServiceException;

}
