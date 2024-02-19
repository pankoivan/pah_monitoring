package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.VertigoTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Vertigo;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("vertigoTableMapper")
public class VertigoToTableDtoMapper implements BaseEntityToOutDtoListMapper<Vertigo, VertigoTableDto> {

    @Override
    public VertigoTableDto map(Vertigo vertigo) {
        return VertigoTableDto
                .builder()
                .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(vertigo.getDate()))
                .duration(vertigo.getDuration().getAlias())
                .nausea(nausea(vertigo.getNausea()))
                .build();
    }

    public String nausea(Boolean nausea) {
        return nausea ? "Да" : "Нет";
    }

}
