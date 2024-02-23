package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PressureGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pressureGraphicMapper")
public class PressureToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Pressure, PressureGraphicDto> {

    @Override
    public PressureGraphicDto map(Pressure pressure) {
        return null;
    }

}
