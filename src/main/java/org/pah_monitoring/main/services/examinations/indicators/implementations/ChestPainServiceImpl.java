package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.ChestPainAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.ChestPainGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.ChestPainTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.ChestPain;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.ChestPainRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("chestPainService")
public class ChestPainServiceImpl extends AbstractInputIndicatorServiceImpl
        <ChestPain, ChestPainAddingDto, ChestPainTablesDto, ChestPainGraphicsDto> {

    private final ChestPainRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<ChestPain> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public ChestPain add(ChestPainAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    ChestPain
                            .builder()
                            .type(addingDto.getType())
                            .duration(addingDto.getDuration())
                            .nitroglycerin(addingDto.getNitroglycerin())
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
    protected ChestPainTablesDto toTablesDto(ChestPain chestPain) {
        return ChestPainTablesDto
                .builder()
                .build();
    }

    @Override
    protected ChestPainGraphicsDto toGraphicsDto(ChestPain chestPain) {
        return ChestPainGraphicsDto
                .builder()
                .build();
    }

}
