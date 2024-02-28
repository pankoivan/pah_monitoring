package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.LiquidGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Liquid;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("liquidGraphicMapper")
public class LiquidToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Liquid, LiquidGraphicDto> {

    @Override
    @NullWhenNull
    public LiquidGraphicDto map(Liquid liquid) {
        return LiquidGraphicDto
                .builder()
                .formattedDate(liquid.getFormattedDate())
                .liquid(String.format("%.2f", liquid.getLiquid()).replaceAll(",", "."))
                .build();
    }

}
