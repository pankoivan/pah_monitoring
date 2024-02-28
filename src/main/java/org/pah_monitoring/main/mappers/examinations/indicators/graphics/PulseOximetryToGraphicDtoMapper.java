package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PulseOximetryGraphicDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pulseOximetryGraphicMapper")
public class PulseOximetryToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<PulseOximetry, PulseOximetryGraphicDto> {

    @Override
    @NullWhenNull
    public PulseOximetryGraphicDto map(PulseOximetry pulseOximetry) {
        return PulseOximetryGraphicDto
                .builder()
                .formattedDate(pulseOximetry.getFormattedDate())
                .oxygenPercentage(String.format("%.2f", pulseOximetry.getOxygenPercentage()).replaceAll(",", "."))
                .pulseRate(String.valueOf(pulseOximetry.getPulseRate()))
                .afterExercise(pulseOximetry.getAfterExercise())
                .build();
    }

}
