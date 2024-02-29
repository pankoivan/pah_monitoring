package org.pah_monitoring.main.dto.out.examinations.indicators.graphics;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class WalkTestGraphicDto implements OutDto {

    private String formattedDate;

    private Double distance;

    private Integer numberOfStops;

    private String breathlessness;

    private PulseOximetryGraphicDto pulseOximetryBefore;

    private PulseOximetryGraphicDto pulseOximetryAfter;

    private PressureGraphicDto pressureBefore;

    private PressureGraphicDto pressureAfter;

}
