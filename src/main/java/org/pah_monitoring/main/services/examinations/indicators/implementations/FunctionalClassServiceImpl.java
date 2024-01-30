package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.FunctionalClassAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.FunctionalClassGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.FunctionalClassTablesDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.FunctionalClass;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.FunctionalClassRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("functionalClassService")
public class FunctionalClassServiceImpl extends AbstractInputIndicatorServiceImpl
        <FunctionalClass, FunctionalClassAddingDto, FunctionalClassTablesDto, FunctionalClassGraphicsDto> {

    private final FunctionalClassRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.FUNCTIONAL_CLASS;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName("functional-class")
                .name(getIndicatorType().getAlias())
                .filename("functional-class.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .tablesViewRef("tables?functional-class")
                .graphicsViewRef("graphics?functional-class")
                .build();
    }

    @Override
    public List<FunctionalClass> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public FunctionalClass add(FunctionalClassAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    FunctionalClass
                            .builder()
                            .functionalClassNumber(addingDto.getFunctionalClassNumber())
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
    protected FunctionalClassTablesDto toTablesDto(FunctionalClass functionalClass) {
        return FunctionalClassTablesDto
                .builder()
                .build();
    }

    @Override
    protected FunctionalClassGraphicsDto toGraphicsDto(FunctionalClass functionalClass) {
        return FunctionalClassGraphicsDto
                .builder()
                .build();
    }

}
