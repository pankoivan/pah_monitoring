package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.CoughAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Cough;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class CoughServiceImpl extends AbstractIndicatorServiceImpl<Cough, CoughAddingDto> {

    @Override
    public Cough add(CoughAddingDto addingDto) throws DataSavingServiceException {
        try {
            return getRepository().save(
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

}
