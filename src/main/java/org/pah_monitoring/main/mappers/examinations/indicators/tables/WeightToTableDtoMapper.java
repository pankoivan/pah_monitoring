package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.WeightTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Weight;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("weightTableMapper")
public class WeightToTableDtoMapper implements BaseEntityToOutDtoListMapper<Weight, WeightTableDto> {

    @Override
    @NullWhenNull
    public WeightTableDto map(Weight weight) {
        return WeightTableDto
                .builder()
                .formattedDate(weight.getFormattedDate())
                .weight(NumberUtils.round(weight.getWeight(), 2))
                .bodyMassIndex(
                        NumberUtils.round(FormulaUtils.bodyMassIndex(weight.getWeight(), weight.getPatient().getAnamnesis().getHeight()), 2)
                )
                .build();
    }

}
