package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.FaintingTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Fainting;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("faintingTableMapper")
public class FaintingToTableDtoMapper implements BaseEntityToOutDtoListMapper<Fainting, FaintingTableDto> {

    @Override
    @NullWhenNull
    public FaintingTableDto map(Fainting fainting) {
        return FaintingTableDto
                .builder()
                .formattedDate(fainting.getFormattedDate())
                .duration(fainting.getDuration().getAlias())
                .duringExercise(duringExercise(fainting.getDuringExercise()))
                .build();
    }

    private String duringExercise(boolean duringExercise) {
        return duringExercise ? "Да" : "Нет";
    }

}
