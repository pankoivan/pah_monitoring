package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pulseOximetryTableMapper")
public class PulseOximetryToTableDtoMapper implements BaseEntityToOutDtoListMapper<PulseOximetry, PulseOximetryTableDto> {

    @Override
    @NullWhenNull
    public PulseOximetryTableDto map(PulseOximetry pulseOximetry) {
        return PulseOximetryTableDto
                .builder()
                .formattedDate(pulseOximetry.getFormattedDate())
                .oxygenPercentage(NumberUtils.round(pulseOximetry.getOxygenPercentage(), 2))
                .pulseRate(NumberUtils.round(pulseOximetry.getPulseRate(), 2))
                .afterExercise(afterExercise(pulseOximetry.getAfterExercise()))
                .build();
    }

    private String afterExercise(Boolean afterExercise) {
        return afterExercise ? "Да" : "Нет";
    }

}
