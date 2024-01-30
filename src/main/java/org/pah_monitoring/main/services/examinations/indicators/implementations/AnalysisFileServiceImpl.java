package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.FileIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.AnalysisFileAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.AnalysisFileRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.examinations.indicators.interfaces.AnalysisFileService;
import org.pah_monitoring.main.services.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AnalysisFileServiceImpl extends AbstractIndicatorServiceImpl<AnalysisFile, AnalysisFileAddingDto>
        implements AnalysisFileService<AnalysisFile, AnalysisFileAddingDto> {

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
    public FileIndicatorCard getFileIndicatorCardFor(AnalysisFile.AnalysisType type, Patient patient) {
        return FileIndicatorCard
                .builder()
                .workingName(type.getWorkingName())
                .name(type.getName())
                .filename(type.getFilename())
                .schedule(getScheduleFor(type, patient).orElse(null))
                .date(getLastExaminationDateFor(type, patient).orElse(null))
                .filesRef(type.getFilesRef())
                .build();
    }

    @Override
    public List<AnalysisFile> findAllByPatientId(AnalysisFile.AnalysisType type, Integer id) throws DataSearchingServiceException {
        return repository.findAllByAnalysisTypeAndPatientId(type, patientService.findById(id).getId());
    }

    @Override
    public AnalysisFile add(AnalysisFileAddingDto addingDto) throws DataSavingServiceException {
        return AnalysisFile
                .builder()
                .build();
    }

}
