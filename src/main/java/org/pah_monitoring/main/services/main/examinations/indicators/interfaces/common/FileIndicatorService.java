package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataDownloadingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileIndicatorService<T extends Indicator> extends IndicatorService<T> {

    Optional<LocalDateTime> getLastExaminationDateFor(AnalysisFile.AnalysisType analysisType, Patient patient);

    Optional<String> getScheduleFor(AnalysisFile.AnalysisType analysisType, Patient patient);

    IndicatorCard getIndicatorCardFor(AnalysisFile.AnalysisType analysisType, Patient patient);

    byte[] download(AnalysisFile analysisFile) throws DataDownloadingServiceException;

    T findByFilename(String filename) throws DataSearchingServiceException;

    List<T> findAllByPatientId(AnalysisFile.AnalysisType analysisType, Integer patientId) throws DataSearchingServiceException;

    List<T> findAllByPatientId(AnalysisFile.AnalysisType analysisType, Integer patientId, String period) throws DataSearchingServiceException;

    T add(AnalysisFile.AnalysisType analysisType, MultipartFile file) throws DataSavingServiceException;

    void checkDataValidityForAdding(MultipartFile file) throws DataValidationServiceException;

}
