package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.PulseOximetryAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.PulseOximetryGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.PulseOximetryTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.PulseOximetry;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.PulseOximetryRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("pulseOximetryService")
public class PulseOximetryServiceImpl extends AbstractIndicatorServiceImpl
        <PulseOximetry, PulseOximetryAddingDto, PulseOximetryTablesDto, PulseOximetryGraphicsDto> {

    private final PulseOximetryRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

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
