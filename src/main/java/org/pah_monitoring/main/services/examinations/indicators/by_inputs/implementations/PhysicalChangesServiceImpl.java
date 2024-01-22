package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.PhysicalChangesAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.PhysicalChanges;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class PhysicalChangesServiceImpl extends AbstractIndicatorServiceImpl<PhysicalChanges, PhysicalChangesAddingDto> {

    @Override
    public PhysicalChanges add(PhysicalChangesAddingDto addingDto) throws DataSavingServiceException {
        try {
            return getRepository().save(
                    PhysicalChanges
                            .builder()
                            .acrocyanosis(addingDto.getAcrocyanosis())
                            .fingersPhalanges(addingDto.getFingersPhalanges())
                            .nails(addingDto.getNails())
                            .chest(addingDto.getChest())
                            .neckVeins(addingDto.getNeckVeins())
                            .lowerExtremities(addingDto.getLowerExtremities())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

}
