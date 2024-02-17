package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.PulseOximetryAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PulseOximetryGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.GraphicsTablesInputIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.PulseOximetryRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.GraphicsTablesInputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("pulseOximetryService")
public class PulseOximetryServiceImpl extends AbstractInputIndicatorServiceImpl<PulseOximetry, PulseOximetryAddingDto>
        implements GraphicsTablesInputIndicatorService<PulseOximetry, PulseOximetryAddingDto, PulseOximetryTablesDto, PulseOximetryGraphicsDto> {

    private final PulseOximetryRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.PULSE_OXIMETRY;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return GraphicsTablesInputIndicatorCard
                .builder()
                .workingName(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("pulse-oximetry.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/pulse-oximetry")
                .tablesLink("/patients/%s/examinations/tables?pulse-oximetry".formatted(patient.getId()))
                .graphicsLink("/patients/%s/examinations/graphics?pulse-oximetry".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<PulseOximetry> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public PulseOximetry add(PulseOximetryAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    PulseOximetry
                            .builder()
                            .oxygenPercentage(addingDto.getOxygenPercentage())
                            .pulseRate(addingDto.getPulseRate())
                            .afterExercise(addingDto.getAfterExercise())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public PulseOximetryTablesDto toTablesOutDto() {
        return null;
    }

    @Override
    public PulseOximetryGraphicsDto toGraphicsOutDto() {
        return null;
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
