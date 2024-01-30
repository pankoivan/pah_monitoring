package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.PhysicalChangesAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.PhysicalChangesGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.PhysicalChangesTablesDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.PhysicalChangesRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("physicalChangesService")
public class PhysicalChangesServiceImpl extends AbstractInputIndicatorServiceImpl
        <PhysicalChanges, PhysicalChangesAddingDto, PhysicalChangesTablesDto, PhysicalChangesGraphicsDto> {

    private final PhysicalChangesRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.PHYSICAL_CHANGES;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName("physical-changes")
                .name(getIndicatorType().getAlias())
                .filename("physical-changes.jpg")
                .postFormRef("/indicators/physical-changes")
                .tablesViewRef("/patients/%s/examinations/tables?physical-changes".formatted(patient.getId()))
                .graphicsViewRef("/patients/%s/examinations/graphics?physical-changes".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

    @Override
    public List<PhysicalChanges> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public PhysicalChanges add(PhysicalChangesAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    PhysicalChanges
                            .builder()
                            .acrocyanosis(addingDto.getAcrocyanosis())
                            .fingersPhalanges(addingDto.getFingersPhalanges())
                            .nails(addingDto.getNails())
                            .chest(addingDto.getChest())
                            .neckVeins(addingDto.getNeckVeins())
                            .lowerExtremities(addingDto.getLowerExtremities())
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
    protected PhysicalChangesTablesDto toTablesDto(PhysicalChanges physicalChanges) {
        return PhysicalChangesTablesDto
                .builder()
                .build();
    }

    @Override
    protected PhysicalChangesGraphicsDto toGraphicsDto(PhysicalChanges physicalChanges) {
        return PhysicalChangesGraphicsDto
                .builder()
                .build();
    }

}
