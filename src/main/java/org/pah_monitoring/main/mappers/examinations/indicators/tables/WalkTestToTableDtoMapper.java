package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PressureTableDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTableDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.WalkTestTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("walkTestTableMapper")
public class WalkTestToTableDtoMapper implements BaseEntityToOutDtoListMapper<WalkTest, WalkTestTableDto> {

    @Qualifier("pulseOximetryTableMapper")
    private BaseEntityToOutDtoMapper<PulseOximetry, PulseOximetryTableDto> pulseOximetryTableMapper;

    @Qualifier("pressureTableMapper")
    private BaseEntityToOutDtoMapper<Pressure, PressureTableDto> pressureTableMapper;

    @Override
    @NullWhenNull
    public WalkTestTableDto map(WalkTest walkTest) {
        return WalkTestTableDto
                .builder()
                .formattedDate(walkTest.getFormattedDate())
                .oxygenSupport(hasNot(walkTest.getOxygenSupport()))
                .auxiliaryDevices(hasNot(walkTest.getAuxiliaryDevices()))
                .distance(String.format("%.2f", walkTest.getDistance()))
                .numberOfStops(String.valueOf(walkTest.getNumberOfStops()))
                .breathlessness(walkTest.getBreathlessness().getAlias())
                .pulseOximetryBefore(pulseOximetryTableMapper.map(walkTest.getPulseOximetryBefore()))
                .pulseOximetryAfter(pulseOximetryTableMapper.map(walkTest.getPulseOximetryAfter()))
                .pressureBefore(pressureTableMapper.map(walkTest.getPressureBefore()))
                .pressureAfter(pressureTableMapper.map(walkTest.getPressureAfter()))
                .build();
    }

    private String hasNot(Boolean bool) {
        return bool ? "Есть" : "Нет";
    }

}
