package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class PulseOximetryTableDto implements OutDto {

    private String formattedDate;

    private String oxygenPercentage;

    private String pulseRate;

    private String afterExercise;

}
