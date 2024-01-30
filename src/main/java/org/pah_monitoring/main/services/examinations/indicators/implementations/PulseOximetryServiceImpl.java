package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.PulseOximetryAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.PulseOximetryGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.PulseOximetryTablesDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.PulseOximetryRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("pulseOximetryService")
public class PulseOximetryServiceImpl extends AbstractInputIndicatorServiceImpl
        <PulseOximetry, PulseOximetryAddingDto, PulseOximetryTablesDto, PulseOximetryGraphicsDto> {

    private final PulseOximetryRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.PULSE_OXIMETRY;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName("pulse-oximetry")
                .name(getIndicatorType().getAlias())
                .filename("pulse-oximetry.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .tablesViewRef("tables?pulse-oximetry")
                .graphicsViewRef("graphics?pulse-oximetry")
                .build();
    }

    @Override
    public List<PulseOximetry> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public PulseOximetry add(PulseOximetryAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    PulseOximetry
                            .builder()
                            .oxygenPercentage(addingDto.getOxygenPercentage())
                            .pulseRate(addingDto.getPulseRate())
                            .duringExercise(addingDto.getDuringExercise())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatientIdAbstract(patient.getId());
    }

    @Override
    protected PulseOximetryTablesDto toTablesDto(PulseOximetry pulseOximetry) {
        return PulseOximetryTablesDto
                .builder()
                .build();
    }

    @Override
    protected PulseOximetryGraphicsDto toGraphicsDto(PulseOximetry pulseOximetry) {
        return PulseOximetryGraphicsDto
                .builder()
                .build();
    }

}
