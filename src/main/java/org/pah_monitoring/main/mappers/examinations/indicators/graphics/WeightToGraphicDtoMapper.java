package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.WeightGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Weight;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("weightGraphicMapper")
public class WeightToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Weight, WeightGraphicDto> {

    @Override
    public WeightGraphicDto map(Weight weight) {
        return null;
    }

}
