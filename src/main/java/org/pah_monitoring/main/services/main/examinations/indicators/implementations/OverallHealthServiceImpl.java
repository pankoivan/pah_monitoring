package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.OverallHealthAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.OverallHealthTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.TableInputIndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.OverallHealth;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.OverallHealthRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.TableInputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("overallHealthService")
public class OverallHealthServiceImpl extends AbstractInputIndicatorServiceImpl<OverallHealth, OverallHealthAddingDto>
        implements TableInputIndicatorService<OverallHealth, OverallHealthAddingDto, OverallHealthTablesDto> {

    private final OverallHealthRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.OVERALL_HEALTH;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return TableInputIndicatorCard
                .builder()
                .workingName(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("overall-health.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/overall-health")
                .tablesLink("/patients/%s/examinations/tables?overall-health".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<OverallHealth> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public OverallHealth add(OverallHealthAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    OverallHealth
                            .builder()
                            .breathlessness(addingDto.getBreathlessness())
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
    public OverallHealthTablesDto toTablesOutDto() {
        return null;
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
