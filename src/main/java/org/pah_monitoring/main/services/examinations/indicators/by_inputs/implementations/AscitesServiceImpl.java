package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.AscitesAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Ascites;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AscitesServiceImpl extends AbstractIndicatorServiceImpl<Ascites, AscitesAddingDto> {

    @Override
    public Ascites add(AscitesAddingDto addingDto) throws DataSavingServiceException {
        try {
            return getRepository().save(
                    Ascites
                            .builder()
                            .liquidAmount(addingDto.getLiquidAmount())
                            .contentInfection(addingDto.getContentInfection())
                            .responseToDrugTherapy(addingDto.getResponseToDrugTherapy())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

}
