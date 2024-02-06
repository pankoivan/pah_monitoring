package org.pah_monitoring.main.services.main.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PressureGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PulseOximetryGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.WalkTestGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PressureTablesDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTablesDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.WalkTestTablesDto;
import org.pah_monitoring.main.entities.additional.indicators.InputIndicatorCard;
import org.pah_monitoring.main.dto.in.examinations.indicators.PressureAddingDto;
import org.pah_monitoring.main.dto.in.examinations.indicators.PulseOximetryAddingDto;
import org.pah_monitoring.main.dto.in.examinations.indicators.WalkTestAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main.examinations.indicators.WalkTestRepository;
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
public class WalkTestServiceImpl extends AbstractInputIndicatorServiceImpl
        <WalkTest, WalkTestAddingDto, WalkTestTablesDto, WalkTestGraphicsDto> {

    private final WalkTestRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Qualifier("pulseOximetryService")
    private AbstractInputIndicatorServiceImpl<PulseOximetry, PulseOximetryAddingDto, PulseOximetryTablesDto, PulseOximetryGraphicsDto>
            pulseOximetryService;

    @Qualifier("pressureService")
    private AbstractInputIndicatorServiceImpl<Pressure, PressureAddingDto, PressureTablesDto, PressureGraphicsDto>
            pressureService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.WALK_TEST;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName(IndicatorType.WALK_TEST.name())
                .name(getIndicatorType().getAlias())
                .filename("walk-test.jpg")
                .postFormRef("/indicators/walk-test")
                .tablesRef("/patients/%s/examinations/tables?walk-test".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?walk-test".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

    @Override
    public List<WalkTest> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
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
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

    @Override
    protected WalkTestTablesDto toTablesDto(WalkTest walkTest) {
        return WalkTestTablesDto
                .builder()
                .build();
    }

    @Override
    protected WalkTestGraphicsDto toGraphicsDto(WalkTest walkTest) {
        return WalkTestGraphicsDto
                .builder()
                .build();
    }

}
