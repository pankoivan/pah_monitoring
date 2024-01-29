package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.PressureAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.PressureGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.PressureTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.PressureRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("pressureService")
public class PressureServiceImpl extends AbstractInputIndicatorServiceImpl
        <Pressure, PressureAddingDto, PressureTablesDto, PressureGraphicsDto> {

    private final PressureRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<Pressure> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Pressure add(PressureAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Pressure
                            .builder()
                            .upper(addingDto.getUpper())
                            .lower(addingDto.getLower())
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
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatientIdAbstract(patient.getId());
    }

    @Override
    protected PressureTablesDto toTablesDto(Pressure pressure) {
        return PressureTablesDto
                .builder()
                .build();
    }

    @Override
    protected PressureGraphicsDto toGraphicsDto(Pressure pressure) {
        return PressureGraphicsDto
                .builder()
                .build();
    }

}
