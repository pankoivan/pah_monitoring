package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.LiquidAndWeightGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.LiquidAndWeightTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.InputIndicatorCard;
import org.pah_monitoring.main.dto.in.examinations.indicators.LiquidAndWeightAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.LiquidAndWeight;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.main.examinations.indicators.LiquidAndWeightRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("liquidAndWeightService")
public class LiquidAndWeightServiceImpl extends AbstractInputIndicatorServiceImpl
        <LiquidAndWeight, LiquidAndWeightAddingDto, LiquidAndWeightTablesDto, LiquidAndWeightGraphicsDto> {

    private final LiquidAndWeightRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.LIQUID_AND_WEIGHT;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName(IndicatorType.LIQUID_AND_WEIGHT.name())
                .name(getIndicatorType().getAlias())
                .filename("liquid-and-weight.jpg")
                .postFormRef("/indicators/liquid-and-weight")
                .tablesRef("/patients/%s/examinations/tables?liquid-and-weight".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?liquid-and-weight".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

    @Override
    public List<LiquidAndWeight> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public LiquidAndWeight add(LiquidAndWeightAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    LiquidAndWeight
                            .builder()
                            .liquid(addingDto.getLiquid())
                            .weight(addingDto.getWeight())
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
    protected LiquidAndWeightTablesDto toTablesDto(LiquidAndWeight liquidAndWeight) {
        return LiquidAndWeightTablesDto
                .builder()
                .build();
    }

    @Override
    protected LiquidAndWeightGraphicsDto toGraphicsDto(LiquidAndWeight liquidAndWeight) {
        return LiquidAndWeightGraphicsDto
                .builder()
                .build();
    }

}
