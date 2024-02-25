package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.additional.indicators.FileIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

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
    public Optional<LocalDateTime> getLastExaminationDateFor(AnalysisFile.AnalysisType type, Patient patient) {
        return repository.findAllByAnalysisTypeAndPatientId(type, patient.getId())
                .stream()
                .map(AnalysisFile::getDate)
                .max(Comparator.comparing(Function.identity()));
    }

    @Override
    public Optional<String> getScheduleFor(AnalysisFile.AnalysisType type, Patient patient) {
        return scheduleService.findConcrete(IndicatorType.valueOf(type.name()), patient).map(ExaminationSchedule::getSchedule);
    }

    @Override
    public IndicatorCard getIndicatorCardFor(AnalysisFile.AnalysisType type, Patient patient) {
        return FileIndicatorCard
                .builder()
                .indicatorType(IndicatorType.valueOf(type.name()))
                .name(type.getName())
                .filename(type.getFilename())
                .schedule(getScheduleFor(type, patient).orElse(null))
                .date(getLastExaminationDateFor(type, patient).orElse(null))
                .postFormLink(type.getPostFormLink())
                .fileLink(type.getFileLink().formatted(patient.getId()))
                .build();
    }

    @Override
    public List<AnalysisFile> findAllByPatientId(AnalysisFile.AnalysisType type, Integer id) throws DataSearchingServiceException {
        return repository.findAllByAnalysisTypeAndPatientId(type, patientService.findById(id).getId());
    }

    @Override
    public AnalysisFile findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Показатель \"Файл\" с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public AnalysisFile add(MultipartFile file, AnalysisFile.AnalysisType analysisType) {
        System.out.println(file.getOriginalFilename());
        System.out.println(analysisType);
        return null;
    }

    @Override
    public void checkDataValidityForAdding(MultipartFile file) throws DataValidationServiceException {
        if (getExtractionService().patient().getDoctor() == null) {
            throw new DataValidationServiceException("""
                    Вы не можете отправлять результаты наблюдений, так как на данный момент за вами не закреплён ни\
                     один врач. Ожидайте, пока администраторы назначат вам какого-нибудь врача, или обратитесь к ним\
                     посредством личных сообщений в случае долгого ожидания
                    """
            );
        }
        if (getExtractionService().patient().hasNoAnamnesis()) {
            throw new DataValidationServiceException("""
                    Вы не можете отправлять результаты наблюдений, так как на данный момент у вас ещё не отправлен анамнез.\
                     Прежде чем отправлять результаты наблюдений, вам необходимо сначала отправить ваш анамнез
                    """
            );
        }
        if (file == null) {
            throw new DataValidationServiceException("Выберите файл");
        }
        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new DataValidationServiceException("Только pdf");
        }
    }

}
