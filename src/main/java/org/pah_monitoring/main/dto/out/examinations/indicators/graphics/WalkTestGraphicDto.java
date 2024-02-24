package org.pah_monitoring.main.dto.out.examinations.indicators.graphics;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PressureTableDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PulseOximetryTableDto;

@Data
@Builder
public class WalkTestGraphicDto implements OutDto {

    private String formattedDate;

    private String distance;

    private String numberOfStops;

    private String breathlessness;

    private PulseOximetryGraphicDto pulseOximetryBefore;

    private PulseOximetryGraphicDto pulseOximetryAfter;

    private PressureGraphicDto pressureBefore;

    private PressureGraphicDto pressureAfter;

}
