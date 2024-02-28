package org.pah_monitoring.main.mappers.examinations.indicators.graphics;

import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PressureGraphicDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PulseOximetryGraphicDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.WalkTestGraphicDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("walkTestGraphicMapper")
public class WalkTestToGraphicDtoMapper implements BaseEntityToOutDtoListMapper<WalkTest, WalkTestGraphicDto> {

    @Qualifier("pulseOximetryGraphicMapper")
    private BaseEntityToOutDtoMapper<PulseOximetry, PulseOximetryGraphicDto> pulseOximetryGraphicMapper;

    @Qualifier("pressureGraphicMapper")
    private BaseEntityToOutDtoMapper<Pressure, PressureGraphicDto> pressureGraphicMapper;

    @Override
    @NullWhenNull
    public WalkTestGraphicDto map(WalkTest walkTest) {
        return WalkTestGraphicDto
                .builder()
                .formattedDate(walkTest.getFormattedDate())
                .distance(NumberUtils.round(walkTest.getDistance(), 2))
                .numberOfStops(walkTest.getNumberOfStops())
                .breathlessness(walkTest.getBreathlessness().getAlias())
                .pulseOximetryBefore(pulseOximetryGraphicMapper.map(walkTest.getPulseOximetryBefore()))
                .pulseOximetryAfter(pulseOximetryGraphicMapper.map(walkTest.getPulseOximetryAfter()))
                .pressureBefore(pressureGraphicMapper.map(walkTest.getPressureBefore()))
                .pressureAfter(pressureGraphicMapper.map(walkTest.getPressureAfter()))
                .build();
    }

}
