package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.WeightGraphicDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.WeightTableDto;
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
                .weight(String.format("%.2f", weight.getWeight()).replaceAll(",", "."))
                .bodyMassIndex(
                        String.format("%.2f", FormulaUtils.bodyMassIndex(weight.getWeight(), weight.getPatient().getAnamnesis().getHeight()))
                                .replaceAll(",", ".")
                )
                .build();
    }

}
