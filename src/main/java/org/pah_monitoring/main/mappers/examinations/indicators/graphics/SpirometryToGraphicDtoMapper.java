package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.SpirometryGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("spirometryGraphicMapper")
public class SpirometryToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<Spirometry, SpirometryGraphicDto> {

    @Override
    public SpirometryGraphicDto map(Spirometry spirometry) {
        return null;
    }

}
