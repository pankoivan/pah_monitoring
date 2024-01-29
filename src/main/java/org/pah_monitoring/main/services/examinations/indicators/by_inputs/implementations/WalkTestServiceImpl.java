package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.PressureAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.PulseOximetryAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.WalkTestAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.PressureGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.PulseOximetryGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.WalkTestGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.PressureTablesDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.PulseOximetryTablesDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.WalkTestTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.examinations.indicators.WalkTest;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.WalkTestRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("walkTestService")
public class WalkTestServiceImpl extends AbstractIndicatorServiceImpl
        <WalkTest, WalkTestAddingDto, WalkTestTablesDto, WalkTestGraphicsDto> {

    private final WalkTestRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Qualifier("pulseOximetryService")
    private AbstractIndicatorServiceImpl<PulseOximetry, PulseOximetryAddingDto, PulseOximetryTablesDto, PulseOximetryGraphicsDto>
            pulseOximetryService;

    @Qualifier("pressureService")
    private AbstractIndicatorServiceImpl<Pressure, PressureAddingDto, PressureTablesDto, PressureGraphicsDto>
            pressureService;

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

    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatientIdAbstract(patient.getId());
    }

}
