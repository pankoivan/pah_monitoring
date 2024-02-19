package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pulseOximetryTableMapper")
public class PulseOximetryToTableDtoMapper implements BaseEntityToOutDtoListMapper<PulseOximetry, PulseOximetryTableDto> {

    @Override
    public PulseOximetryTableDto map(PulseOximetry pulseOximetry) {
        return PulseOximetryTableDto
                .builder()
                .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(pulseOximetry.getDate()))
                .oxygenPercentage(String.format("%.2f", pulseOximetry.getOxygenPercentage()))
                .pulseRate(String.valueOf(pulseOximetry.getPulseRate()))
                .afterExercise(afterExercise(pulseOximetry.getAfterExercise()))
                .build();
    }

    private String afterExercise(Boolean afterExercise) {
        return afterExercise ? "Да" : "Нет";
    }

}
