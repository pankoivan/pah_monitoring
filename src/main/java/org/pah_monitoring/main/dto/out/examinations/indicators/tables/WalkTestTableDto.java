package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.entities.main.examinations.indicators.PulseOximetry;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;

@Data
@Builder
public class WalkTestTableDto {

    private String formattedDate;

    private String oxygenSupport;

    private String auxiliaryDevices;

    private String distance;

    private String numberOfStops;

    private String breathlessness;

    private PulseOximetry pulseOximetryBefore;

    private PulseOximetry pulseOximetryAfter;

    private Pressure pressureBefore;

    private Pressure pressureAfter;

}
