package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalkTestTableDto {

    private String formattedDate;

    private String oxygenSupport;

    private String auxiliaryDevices;

    private String distance;

    private String numberOfStops;

    private String breathlessness;

    private PulseOximetryTableDto pulseOximetryBefore;

    private PulseOximetryTableDto pulseOximetryAfter;

    private PressureTableDto pressureBefore;

    private PressureTableDto pressureAfter;

}
