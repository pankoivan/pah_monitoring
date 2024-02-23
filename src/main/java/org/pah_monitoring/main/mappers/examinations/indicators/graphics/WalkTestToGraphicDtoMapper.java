package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.WalkTestGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("walkTestGraphicMapper")
public class WalkTestToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<WalkTest, WalkTestGraphicDto> {

    @Override
    public WalkTestGraphicDto map(WalkTest walkTest) {
        return null;
    }

}
