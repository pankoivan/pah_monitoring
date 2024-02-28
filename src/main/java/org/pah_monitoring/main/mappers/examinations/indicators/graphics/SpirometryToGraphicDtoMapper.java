package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.SpirometryGraphicDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.SpirometryTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("spirometryGraphicMapper")
public class SpirometryToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Spirometry, SpirometryGraphicDto> {

    @Override
    @NullWhenNull
    public SpirometryGraphicDto map(Spirometry spirometry) {
        return SpirometryGraphicDto
                .builder()
                .formattedDate(spirometry.getFormattedDate())
                .vlc(String.format("%.2f", spirometry.getVlc()).replaceAll(",", "."))
                .avlc(String.format("%.2f", spirometry.getAvlc()).replaceAll(",", "."))
                .rlv(String.format("%.2f", spirometry.getRlv()).replaceAll(",", "."))
                .vfe1(String.format("%.2f", spirometry.getVfe1()).replaceAll(",", "."))
                .tlc(String.format("%.2f", FormulaUtils.tlc(spirometry.getRlv(), spirometry.getVlc())).replaceAll(",", "."))
                .tiffnoIndex(String.format("%.2f", FormulaUtils.tiffnoIndex(spirometry.getVfe1(), spirometry.getVlc())).replaceAll(",", "."))
                .build();
    }

}
