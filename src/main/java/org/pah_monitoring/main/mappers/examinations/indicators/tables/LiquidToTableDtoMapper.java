package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.LiquidTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Liquid;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("liquidTableMapper")
public class LiquidToTableDtoMapper implements BaseEntityToOutDtoListMapper<Liquid, LiquidTableDto> {

    @Override
    @NullWhenNull
    public LiquidTableDto map(Liquid liquid) {
        return LiquidTableDto
                .builder()
                .formattedDate(liquid.getFormattedDate())
                .liquid(NumberUtils.round(liquid.getLiquid(), 2))
                .build();
    }

}
