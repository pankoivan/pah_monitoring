package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaintingTableDto {

    private String formattedDate;

    private String duration;

    private String duringExercise;

}
