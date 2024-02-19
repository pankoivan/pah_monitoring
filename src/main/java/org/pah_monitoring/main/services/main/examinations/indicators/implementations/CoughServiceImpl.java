package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.CoughAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.CoughTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.TableInputIndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Cough;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.CoughRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("coughService")
public class CoughServiceImpl extends AbstractInputIndicatorServiceImpl<Cough, CoughAddingDto> {

    private final CoughRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.COUGH;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return TableInputIndicatorCard
                .builder()
                .indicatorType(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("cough.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/form/cough")
                .tableLink("/patients/%s/examinations/tables/cough".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<Cough> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
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
    protected List<Indicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
