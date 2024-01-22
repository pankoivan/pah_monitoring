package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.PressureAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.PulseOximetryAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.WalkTestAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Pressure;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.PulseOximetry;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.WalkTest;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class WalkTestServiceImpl extends AbstractIndicatorServiceImpl<WalkTest, WalkTestAddingDto> {

    @Qualifier("pulseOximetryService")
    private AbstractIndicatorServiceImpl<PulseOximetry, PulseOximetryAddingDto> pulseOximetryService;

    @Qualifier("pressureService")
    private AbstractIndicatorServiceImpl<Pressure, PressureAddingDto> pressureService;

    @Override
    public WalkTest add(WalkTestAddingDto addingDto) throws DataSavingServiceException {
        try {
            return getRepository().save(
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

}
