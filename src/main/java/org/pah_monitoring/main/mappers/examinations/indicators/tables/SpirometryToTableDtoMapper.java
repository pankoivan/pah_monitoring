package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.SpirometryTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("spirometryTableMapper")
public class SpirometryToTableDtoMapper implements BaseEntityToOutDtoListMapper<Spirometry, SpirometryTableDto> {

    @Override
    @NullWhenNull
    public SpirometryTableDto map(Spirometry spirometry) {
        return SpirometryTableDto
                .builder()
                .formattedDate(spirometry.getFormattedDate())
                .vlc(NumberUtils.round(spirometry.getVlc(), 2))
                .avlc(NumberUtils.round(spirometry.getAvlc(), 2))
                .rlv(NumberUtils.round(spirometry.getRlv(), 2))
                .vfe1(NumberUtils.round(spirometry.getVfe1(), 2))
                .tlc(NumberUtils.round(FormulaUtils.tlc(spirometry.getRlv(), spirometry.getVlc()), 2))
                .tiffnoIndex(NumberUtils.round(FormulaUtils.tiffnoIndex(spirometry.getVfe1(), spirometry.getVlc()), 2))
                .build();
    }

}
