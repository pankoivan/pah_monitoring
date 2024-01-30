package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.indicators.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.OverallHealthAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.OverallHealthGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.OverallHealthTablesDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.OverallHealth;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.OverallHealthRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("overallHealthService")
public class OverallHealthServiceImpl extends AbstractInputIndicatorServiceImpl
        <OverallHealth, OverallHealthAddingDto, OverallHealthTablesDto, OverallHealthGraphicsDto> {

    private final OverallHealthRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.OVERALL_HEALTH;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName("overall-health")
                .name(getIndicatorType().getAlias())
                .filename("overall-health.jpg")
                .postFormRef("/indicators/overall-health")
                .tablesRef("/patients/%s/examinations/tables?overall-health".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?overall-health".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

    @Override
    public List<OverallHealth> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public OverallHealth add(OverallHealthAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    OverallHealth
                            .builder()
                            .fatigue(addingDto.getFatigue())
                            .restFeeling(addingDto.getRestFeeling())
                            .drowsiness(addingDto.getDrowsiness())
                            .concentration(addingDto.getConcentration())
                            .weakness(addingDto.getWeakness())
                            .appetite(addingDto.getAppetite())
                            .coldExtremities(addingDto.getColdExtremities())
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
        return repository.findAllByPatient(patient);
    }

    @Override
    protected OverallHealthTablesDto toTablesDto(OverallHealth overallHealth) {
        return OverallHealthTablesDto
                .builder()
                .build();
    }

    @Override
    protected OverallHealthGraphicsDto toGraphicsDto(OverallHealth overallHealth) {
        return OverallHealthGraphicsDto
                .builder()
                .build();
    }

}
