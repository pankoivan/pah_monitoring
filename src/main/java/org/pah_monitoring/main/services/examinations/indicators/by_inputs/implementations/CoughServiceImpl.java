package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.CoughAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.CoughGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.CoughTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.Cough;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.CoughRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("coughService")
public class CoughServiceImpl extends AbstractIndicatorServiceImpl
        <Cough, CoughAddingDto, CoughTablesDto, CoughGraphicsDto> {

    private final CoughRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<Cough> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Cough add(CoughAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Cough
                            .builder()
                            .type(addingDto.getType())
                            .power(addingDto.getPower())
                            .timbre(addingDto.getTimbre())
                            .hemoptysis(addingDto.getHemoptysis())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    protected CoughTablesDto toTablesDto(Cough cough) {
        return CoughTablesDto
                .builder()
                .build();
    }

    @Override
    protected CoughGraphicsDto toGraphicsDto(Cough cough) {
        return CoughGraphicsDto
                .builder()
                .build();
    }

    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatientIdAbstract(patient.getId());
    }

}
