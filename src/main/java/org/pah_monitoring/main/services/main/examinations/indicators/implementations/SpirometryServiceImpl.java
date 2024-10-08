package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.SpirometryAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.additional.indicators.GraphicTableInputIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
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
public class SpirometryServiceImpl extends AbstractInputIndicatorServiceImpl<Spirometry, SpirometryAddingDto> {

    private final SpirometryRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.SPIROMETRY;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return GraphicTableInputIndicatorCard
                .builder()
                .indicatorType(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("spirometry.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/forms/spirometry")
                .tableLink("/patients/%s/examinations/tables/spirometry".formatted(patient.getId()))
                .graphicLink("/patients/%s/examinations/graphics/spirometry".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<Spirometry> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public Spirometry findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Показателя \"Спирометрия\" с id \"%s\" не существует".formatted(id))
        );
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
    protected List<Indicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
