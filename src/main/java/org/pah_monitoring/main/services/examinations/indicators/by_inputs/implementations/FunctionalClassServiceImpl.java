package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.FunctionalClassAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.FunctionalClass;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class FunctionalClassServiceImpl extends AbstractIndicatorServiceImpl<FunctionalClass, FunctionalClassAddingDto> {

    @Override
    public FunctionalClass add(FunctionalClassAddingDto addingDto) throws DataSavingServiceException {
        try {
            return getRepository().save(
                    FunctionalClass
                            .builder()
                            .functionalClassNumber(addingDto.getFunctionalClassNumber())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

}
