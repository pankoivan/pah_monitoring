package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.indicators.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.VertigoAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.VertigoGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.VertigoTablesDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.Vertigo;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.VertigoRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("vertigoService")
public class VertigoServiceImpl extends AbstractInputIndicatorServiceImpl
        <Vertigo, VertigoAddingDto, VertigoTablesDto, VertigoGraphicsDto> {

    private final VertigoRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.VERTIGO;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName(IndicatorType.VERTIGO.name())
                .name(getIndicatorType().getAlias())
                .filename("vertigo.jpg")
                .postFormRef("/indicators/vertigo")
                .tablesRef("/patients/%s/examinations/tables?vertigo".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?vertigo".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

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
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
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
