package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.PressureAddingDto;
import org.pah_monitoring.main.dto.in.examinations.indicators.PulseOximetryAddingDto;
import org.pah_monitoring.main.dto.in.examinations.indicators.WalkTestAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.additional.indicators.GraphicTableInputIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.WalkTestRepository;
import org.pah_monitoring.main.services.main.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("walkTestService")
public class WalkTestServiceImpl extends AbstractInputIndicatorServiceImpl<WalkTest, WalkTestAddingDto> {

    private final WalkTestRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Qualifier("pulseOximetryService")
    private AbstractInputIndicatorServiceImpl<PulseOximetry, PulseOximetryAddingDto> pulseOximetryService;

    @Qualifier("pressureService")
    private AbstractInputIndicatorServiceImpl<Pressure, PressureAddingDto> pressureService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.WALK_TEST;
    }

    @Override
    public IndicatorCard getIndicatorCardFor(Patient patient) {
        return GraphicTableInputIndicatorCard
                .builder()
                .indicatorType(getIndicatorType())
                .name(getIndicatorType().getAlias())
                .filename("walk-test.jpg")
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .postFormLink("/indicators/form/walk-test")
                .tableLink("/patients/%s/examinations/tables/walk-test".formatted(patient.getId()))
                .graphicLink("/patients/%s/examinations/graphics/walk-test".formatted(patient.getId()))
                .build();
    }

    @Override
    public List<WalkTest> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
    }

    @Override
    public WalkTest add(WalkTestAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    WalkTest
                            .builder()
                            .oxygenSupport(addingDto.getOxygenSupport())
                            .auxiliaryDevices(addingDto.getAuxiliaryDevices())
                            .distance(addingDto.getDistance())
                            .numberOfStops(addingDto.getNumberOfStops())
                            .breathlessness(addingDto.getBreathlessness())
                            .pulseOximetryBefore(pulseOximetryService.add(addingDto.getPulseOximetryBefore()))
                            .pulseOximetryAfter(pulseOximetryService.add(addingDto.getPulseOximetryAfter()))
                            .pressureBefore(pressureService.add(addingDto.getPressureBefore()))
                            .pressureAfter(pressureService.add(addingDto.getPressureAfter()))
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(WalkTestAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        super.checkDataValidityForAdding(addingDto, bindingResult);
        pulseOximetryService.checkDataValidityForAdding(addingDto.getPulseOximetryBefore(), bindingResult);
        pulseOximetryService.checkDataValidityForAdding(addingDto.getPulseOximetryAfter(), bindingResult);
        pressureService.checkDataValidityForAdding(addingDto.getPressureBefore(), bindingResult);
        pressureService.checkDataValidityForAdding(addingDto.getPressureAfter(), bindingResult);
    }

    @Override
    protected List<Indicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

}
