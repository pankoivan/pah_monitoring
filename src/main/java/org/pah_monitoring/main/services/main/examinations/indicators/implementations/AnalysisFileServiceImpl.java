package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.additional.indicators.FileIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataDownloadingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.AnalysisFileRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.pah_monitoring.main.services.main.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("analysisFileService")
public class AnalysisFileServiceImpl extends AbstractIndicatorServiceImpl<AnalysisFile> implements FileIndicatorService<AnalysisFile> {

    @Value("${my.file.upload-path}")
    private final String uploadPath;

    @Value("${my.file.allowed-extensions}")
    private final List<String> allowedExtensions;

    private final AnalysisFileRepository repository;

    private ExaminationScheduleService scheduleService;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public Optional<LocalDateTime> getLastExaminationDateFor(AnalysisFile.AnalysisType analysisType, Patient patient) {
        return repository.findAllByAnalysisTypeAndPatientId(analysisType, patient.getId())
                .stream()
                .map(AnalysisFile::getDate)
                .max(Comparator.comparing(Function.identity()));
    }

    @Override
    public Optional<String> getScheduleFor(AnalysisFile.AnalysisType analysisType, Patient patient) {
        return scheduleService.findConcrete(IndicatorType.valueOf(analysisType.name()), patient).map(ExaminationSchedule::getSchedule);
    }

    @Override
    public IndicatorCard getIndicatorCardFor(AnalysisFile.AnalysisType analysisType, Patient patient) {
        return FileIndicatorCard
                .builder()
                .indicatorType(IndicatorType.valueOf(analysisType.name()))
                .name(analysisType.getName())
                .filename(analysisType.getFilename())
                .schedule(getScheduleFor(analysisType, patient).orElse(null))
                .date(getLastExaminationDateFor(analysisType, patient).orElse(null))
                .postFormLink(analysisType.getPostFormLink())
                .fileLink(analysisType.getFileLink().formatted(patient.getId()))
                .build();
    }

    @Override
    public byte[] download(AnalysisFile analysisFile) throws DataDownloadingServiceException {
        try {
            return Files.readAllBytes(Paths.get(uploadPath, analysisFile.getFilename()));
        } catch (IOException e) {
            throw new DataDownloadingServiceException(
                    "Произошла ошибка при скачивании файла \"%s\" с сервера".formatted(analysisFile.getFilename()), e
            );
        }
    }

    @Override
    public AnalysisFile findByFilename(String filename) throws DataSearchingServiceException {
        return repository.findByFilename(filename).orElseThrow(
                () -> new DataSearchingServiceException("Показателя \"Файл\" с названием \"%s\" не существует".formatted(filename))
        );
    }

    @Override
    public List<AnalysisFile> findAllByPatientId(AnalysisFile.AnalysisType analysisType, Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByAnalysisTypeAndPatientId(analysisType, patientService.findById(patientId).getId());
    }

    @Override
    public List<AnalysisFile> findAllByPatientId(AnalysisFile.AnalysisType analysisType, Integer patientId, String period)
            throws DataSearchingServiceException {
        return overPeriod(findAllByPatientId(analysisType, patientId), period);
    }

    @Override
    public AnalysisFile findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Показателя \"Файл\" с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public AnalysisFile add(AnalysisFile.AnalysisType analysisType, MultipartFile file) throws DataSavingServiceException {
        try {
            String filename = generateFilename(file);
            file.transferTo(Paths.get(uploadPath, filename));
            return repository.save(AnalysisFile
                    .builder()
                    .filename(filename)
                    .analysisType(analysisType)
                    .date(LocalDateTime.now())
                    .patient(getExtractionService().patient())
                    .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("Файл \"%s\" не был сохранён".formatted(file), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(MultipartFile file) throws DataValidationServiceException {
        checkHasNoAnamnesis();
        checkHasNoDoctor();
        if (file == null) {
            throw new DataValidationServiceException("Файл не выбран");
        }
        if (!allowedExtensions.contains(getExtension(file))) {
            throw new DataValidationServiceException("Поддерживаемые форматы: %s".formatted(String.join(", ", allowedExtensions)));
        }
    }

    private String generateFilename(MultipartFile file) {
        Patient patient = getExtractionService().patient();
        return "%s (%s) %s.%s".formatted(
                patient.getUserInformation().getLastname(),
                patient.getId(),
                DateTimeFormatConstants.DAY_MONTH_YEAR_WHITESPACE_HOUR_MINUTE_SECOND.format(LocalDateTime.now()).replaceAll(":", "."),
                getExtension(file)
        );
    }

    private String getExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename.contains(".")
                ? filename.substring(filename.lastIndexOf(".") + 1)
                : "";
    }

}
