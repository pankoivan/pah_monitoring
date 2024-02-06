package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.ChestPainGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.ChestPainTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.InputIndicatorCard;
import org.pah_monitoring.main.dto.in.examinations.indicators.ChestPainAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.ChestPain;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.main.examinations.indicators.ChestPainRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
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
    public IndicatorType getIndicatorType() {
        return IndicatorType.CHEST_PAIN;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName(IndicatorType.CHEST_PAIN.name())
                .name(getIndicatorType().getAlias())
                .filename("chest-pain.jpg")
                .postFormRef("/indicators/chest-pain")
                .tablesRef("/patients/%s/examinations/tables?chest-pain".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?chest-pain".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

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
        return repository.findAllByPatient(patient);
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
