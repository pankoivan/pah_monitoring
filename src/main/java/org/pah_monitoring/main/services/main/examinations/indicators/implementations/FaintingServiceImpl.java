package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.FaintingAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.FaintingTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.TableInputIndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Fainting;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.FaintingRepository;
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
@Service("faintingService")
public class FaintingServiceImpl extends AbstractInputIndicatorServiceImpl
        <Fainting, FaintingAddingDto> implements TableInputIndicatorService<Fainting, FaintingAddingDto, FaintingTablesDto> {

    private final FaintingRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.FAINTING;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return TableInputIndicatorCard
                .builder()
                .indicatorType(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("fainting.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/form/fainting")
                .tableLink("/patients/%s/examinations/tables?fainting".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<Fainting> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public Fainting add(FaintingAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Fainting
                            .builder()
                            .duration(addingDto.getDuration())
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
    public FaintingTablesDto toTablesOutDto() {
        return null;
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
