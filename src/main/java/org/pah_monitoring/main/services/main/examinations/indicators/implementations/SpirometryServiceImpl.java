package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.SpirometryGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.SpirometryTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.InputIndicatorCard;
import org.pah_monitoring.main.dto.in.examinations.indicators.SpirometryAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.SpirometryRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("spirometryService")
public class SpirometryServiceImpl extends AbstractInputIndicatorServiceImpl
        <Spirometry, SpirometryAddingDto, SpirometryTablesDto, SpirometryGraphicsDto> {

    private final SpirometryRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.SPIROMETRY;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName(IndicatorType.SPIROMETRY.name())
                .name(getIndicatorType().getAlias())
                .filename("spirometry.jpg")
                .postFormLink("/indicators/spirometry")
                .tablesLink("/patients/%s/examinations/tables?spirometry".formatted(patient.getId()))
                .graphicsLink("/patients/%s/examinations/graphics?spirometry".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

    @Override
    public List<Spirometry> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Spirometry add(SpirometryAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Spirometry
                            .builder()
                            .vlc(addingDto.getVlc())
                            .avlc(addingDto.getAvlc())
                            .rlv(addingDto.getRlv())
                            .vfe1(addingDto.getVfe1())
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
    protected SpirometryTablesDto toTablesDto(Spirometry spirometry) {
        return SpirometryTablesDto
                .builder()
                .build();
    }

    @Override
    protected SpirometryGraphicsDto toGraphicsDto(Spirometry spirometry) {
        return SpirometryGraphicsDto
                .builder()
                .build();
    }

}
