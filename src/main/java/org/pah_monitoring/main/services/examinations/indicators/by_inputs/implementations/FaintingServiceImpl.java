package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.FaintingAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.FaintingGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.FaintingTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Fainting;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.FaintingRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("faintingService")
public class FaintingServiceImpl extends AbstractIndicatorServiceImpl
        <Fainting, FaintingAddingDto, FaintingTablesDto, FaintingGraphicsDto> {

    private final FaintingRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<Fainting> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Fainting add(FaintingAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Fainting
                            .builder()
                            .duration(addingDto.getDuration())
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
    protected FaintingTablesDto toTablesDto(Fainting fainting) {
        return FaintingTablesDto
                .builder()
                .build();
    }

    @Override
    protected FaintingGraphicsDto toGraphicsDto(Fainting fainting) {
        return FaintingGraphicsDto
                .builder()
                .build();
    }

}
