package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.main.dto.out.examinations.indicators.tables.LiquidTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Liquid;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("liquidTableMapper")
public class LiquidToTableDtoMapper implements BaseEntityToOutDtoListMapper<Liquid, LiquidTableDto> {

    @Override
    public LiquidTableDto map(Liquid liquid) {
        return LiquidTableDto
                .builder()
                .formattedDate(liquid.getFormattedDate())
                .liquid(String.format("%.2f", liquid.getLiquid()))
                .build();
    }

}
