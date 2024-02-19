package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.SpirometryTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("spirometryTableMapper")
public class SpirometryToTableDtoMapper implements BaseEntityToOutDtoListMapper<Spirometry, SpirometryTableDto> {

    @Override
    public SpirometryTableDto map(Spirometry spirometry) {
        return SpirometryTableDto
                .builder()
                .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(spirometry.getDate()))
                .vlc(String.format("%.2f", spirometry.getVlc()))
                .avlc(String.format("%.2f", spirometry.getAvlc()))
                .rlv(String.format("%.2f", spirometry.getRlv()))
                .vfe1(String.format("%.2f", spirometry.getVfe1()))
                .tlc(String.format("%.2f", FormulaUtils.tlc(spirometry.getRlv(), spirometry.getVlc())))
                .tiffnoIndex(String.format("%.2f", FormulaUtils.tiffnoIndex(spirometry.getVfe1(), spirometry.getVlc())))
                .build();
    }

}
