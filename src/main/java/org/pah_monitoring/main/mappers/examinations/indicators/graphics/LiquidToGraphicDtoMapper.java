package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.LiquidGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Liquid;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("liquidGraphicMapper")
public class LiquidToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Liquid, LiquidGraphicDto> {

    @Override
    public LiquidGraphicDto map(Liquid liquid) {
        return null;
    }

}
