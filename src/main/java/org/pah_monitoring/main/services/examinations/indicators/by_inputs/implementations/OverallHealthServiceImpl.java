package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.OverallHealthAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.OverallHealth;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class OverallHealthServiceImpl extends AbstractIndicatorServiceImpl<OverallHealth, OverallHealthAddingDto> {

    @Override
    public OverallHealth add(OverallHealthAddingDto addingDto) throws DataSavingServiceException {
        try {
            return getRepository().save(
                    OverallHealth
                            .builder()
                            .fatigue(addingDto.getFatigue())
                            .restFeeling(addingDto.getRestFeeling())
                            .drowsiness(addingDto.getDrowsiness())
                            .concentration(addingDto.getConcentration())
                            .weakness(addingDto.getWeakness())
                            .appetite(addingDto.getAppetite())
                            .coldExtremities(addingDto.getColdExtremities())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

}
