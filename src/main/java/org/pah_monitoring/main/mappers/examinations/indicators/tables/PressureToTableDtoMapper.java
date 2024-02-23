package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PressureTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pressureTableMapper")
public class PressureToTableDtoMapper implements BaseEntityToOutDtoListMapper<Pressure, PressureTableDto> {

    @Override
    public PressureTableDto map(Pressure pressure) {
        return PressureTableDto
                .builder()
                .formattedDate(pressure.getFormattedDate())
                .upper(String.valueOf(pressure.getUpper()))
                .lower(String.valueOf(pressure.getLower()))
                .afterExercise(afterExercise(pressure.getAfterExercise()))
                .build();
    }

    private String afterExercise(Boolean afterExercise) {
        return afterExercise ? "Да" : "Нет";
    }

}
