package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.VertigoAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.VertigoGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.VertigoTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Vertigo;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.VertigoRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("vertigoService")
public class VertigoServiceImpl extends AbstractIndicatorServiceImpl
        <Vertigo, VertigoAddingDto, VertigoTablesDto, VertigoGraphicsDto> {

    private final VertigoRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<Vertigo> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Vertigo add(VertigoAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Vertigo
                            .builder()
                            .duration(addingDto.getDuration())
                            .nausea(addingDto.getNausea())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    protected VertigoTablesDto toTablesDto(Vertigo vertigo) {
        return VertigoTablesDto
                .builder()
                .build();
    }

    @Override
    protected VertigoGraphicsDto toGraphicsDto(Vertigo vertigo) {
        return VertigoGraphicsDto
                .builder()
                .build();
    }

}
