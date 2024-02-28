package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PressureGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pressureGraphicMapper")
public class PressureToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Pressure, PressureGraphicDto> {

    @Override
    @NullWhenNull
    public PressureGraphicDto map(Pressure pressure) {
        return PressureGraphicDto
                .builder()
                .formattedDate(pressure.getFormattedDate())
                .upper(pressure.getUpper())
                .lower(pressure.getLower())
                .afterExercise(pressure.getAfterExercise())
                .build();
    }

}
