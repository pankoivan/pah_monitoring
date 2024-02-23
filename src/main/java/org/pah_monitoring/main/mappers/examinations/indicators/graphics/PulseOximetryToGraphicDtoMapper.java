package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PulseOximetryGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("pulseOximetryGraphicMapper")
public class PulseOximetryToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<PulseOximetry, PulseOximetryGraphicDto> {

    @Override
    public PulseOximetryGraphicDto map(PulseOximetry pulseOximetry) {
        return null;
    }

}
