package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class WalkTestTableDto implements OutDto {

    private String formattedDate;

    private String oxygenSupport;

    private String auxiliaryDevices;

    private Double distance;

    private Integer numberOfStops;

    private String breathlessness;

    private PulseOximetryTableDto pulseOximetryBefore;

    private PulseOximetryTableDto pulseOximetryAfter;

    private PressureTableDto pressureBefore;

    private PressureTableDto pressureAfter;

}
