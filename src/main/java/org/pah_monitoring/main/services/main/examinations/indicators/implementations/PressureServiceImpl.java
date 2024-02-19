package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.PressureAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PressureGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PressureTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.GraphicTableInputIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.PressureRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.GraphicTableInputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("pressureService")
public class PressureServiceImpl extends AbstractInputIndicatorServiceImpl<Pressure, PressureAddingDto>
        implements GraphicTableInputIndicatorService<Pressure, PressureAddingDto, List<PressureTablesDto>, PressureGraphicsDto> {

    private final PressureRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.PRESSURE;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return GraphicTableInputIndicatorCard
                .builder()
                .workingName(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("pressure.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/form/pressure")
                .tablesLink("/patients/%s/examinations/tables?pressure".formatted(patient.getId()))
                .graphicsLink("/patients/%s/examinations/graphics?pressure".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<Pressure> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public Pressure add(PressureAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Pressure
                            .builder()
                            .upper(addingDto.getUpper())
                            .lower(addingDto.getLower())
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
    public List<PressureTablesDto> toTablesOutDto() {
        return null;
    }

    @Override
    public PressureGraphicsDto toGraphicsOutDto() {
        return null;
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
