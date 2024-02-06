package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.FunctionalClassGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.FunctionalClassTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.InputIndicatorCard;
import org.pah_monitoring.main.dto.in.examinations.indicators.FunctionalClassAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.FunctionalClass;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.main.examinations.indicators.FunctionalClassRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
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
                .workingName(IndicatorType.FUNCTIONAL_CLASS.name())
                .name(getIndicatorType().getAlias())
                .filename("functional-class.jpg")
                .postFormRef("/indicators/functional-class")
                .tablesRef("/patients/%s/examinations/tables?functional-class".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?functional-class".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
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
        return repository.findAllByPatient(patient);
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
