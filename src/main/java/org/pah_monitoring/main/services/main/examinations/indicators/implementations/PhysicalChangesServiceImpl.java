package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.PhysicalChangesAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PhysicalChangesTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.TableInputIndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.PhysicalChangesRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.TableInputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("physicalChangesService")
public class PhysicalChangesServiceImpl extends AbstractInputIndicatorServiceImpl<PhysicalChanges, PhysicalChangesAddingDto>
        implements TableInputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto, PhysicalChangesTablesDto> {

    private final PhysicalChangesRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.PHYSICAL_CHANGES;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return TableInputIndicatorCard
                .builder()
                .workingName(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("physical-changes.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/form/physical-changes")
                .tablesLink("/patients/%s/examinations/tables?physical-changes".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<PhysicalChanges> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public PhysicalChanges add(PhysicalChangesAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    PhysicalChanges
                            .builder()
                            .abdominalEnlargement(addingDto.getAbdominalEnlargement())
                            .legsSwelling(addingDto.getLegsSwelling())
                            .vascularAsterisks(addingDto.getVascularAsterisks())
                            .skinColor(addingDto.getSkinColor())
                            .fingersPhalanges(addingDto.getFingersPhalanges())
                            .chest(addingDto.getChest())
                            .neckVeins(addingDto.getNeckVeins())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public PhysicalChangesTablesDto toTablesOutDto() {
        return null;
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
