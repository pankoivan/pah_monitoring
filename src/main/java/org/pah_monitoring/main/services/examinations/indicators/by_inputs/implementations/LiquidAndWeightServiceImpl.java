package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.LiquidAndWeightAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.LiquidAndWeightGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.LiquidAndWeightTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.LiquidAndWeight;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.LiquidAndWeightRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("liquidAndWeightService")
public class LiquidAndWeightServiceImpl extends AbstractIndicatorServiceImpl
        <LiquidAndWeight, LiquidAndWeightAddingDto, LiquidAndWeightTablesDto, LiquidAndWeightGraphicsDto> {

    private final LiquidAndWeightRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

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

    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatientIdAbstract(patient.getId());
    }

}
