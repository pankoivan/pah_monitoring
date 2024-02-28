package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.WeightGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Weight;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("weightGraphicMapper")
public class WeightToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Weight, WeightGraphicDto> {

    @Override
    @NullWhenNull
    public WeightGraphicDto map(Weight weight) {
        return WeightGraphicDto
                .builder()
                .formattedDate(weight.getFormattedDate())
                .weight(NumberUtils.round(weight.getWeight(), 2))
                .bodyMassIndex(
                        NumberUtils.round(FormulaUtils.bodyMassIndex(weight.getWeight(), weight.getPatient().getAnamnesis().getHeight()), 2)
                )
                .build();
    }

}
