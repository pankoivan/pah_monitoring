package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PulseOximetryTableDto {

    private String formattedDate;

    private String oxygenPercentage;

    private String pulseRate;

    private String afterExercise;

}
