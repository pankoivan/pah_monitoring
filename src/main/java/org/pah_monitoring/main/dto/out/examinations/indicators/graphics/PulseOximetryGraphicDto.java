package org.pah_monitoring.main.dto.out.examinations.indicators.graphics;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class PulseOximetryGraphicDto implements OutDto {

    private String formattedDate;

    private Double oxygenPercentage;

    private Integer pulseRate;

    private Boolean afterExercise;

}
