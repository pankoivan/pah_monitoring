package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.CoughTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Cough;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("coughTableMapper")
public class CoughToTableDtoMapper implements BaseEntityToOutDtoListMapper<Cough, CoughTableDto> {

    @Override
    public CoughTableDto map(Cough cough) {
        return CoughTableDto
                .builder()
                .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(cough.getDate()))
                .type(cough.getType().getAlias())
                .power(cough.getPower().getAlias())
                .timbre(cough.getTimbre().getAlias())
                .hemoptysis(hemoptysis(cough.getHemoptysis()))
                .build();
    }

    private String hemoptysis(Boolean hemoptysis) {
        return hemoptysis ? "Есть" : "Нет";
    }

}
