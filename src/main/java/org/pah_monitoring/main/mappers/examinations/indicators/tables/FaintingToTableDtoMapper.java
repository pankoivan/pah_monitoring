package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.FaintingTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Fainting;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("faintingTableMapper")
public class FaintingToTableDtoMapper implements BaseEntityToOutDtoListMapper<Fainting, FaintingTableDto> {

    @Override
    public FaintingTableDto map(Fainting fainting) {
        return FaintingTableDto
                .builder()
                .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(fainting.getDate()))
                .duration(fainting.getDuration().getAlias())
                .duringExercise(duringExercise(fainting.getDuringExercise()))
                .build();
    }

    private String duringExercise(Boolean duringExercise) {
        return duringExercise ? "Да" : "Нет";
    }

}
