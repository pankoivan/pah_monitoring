package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeightTableDto {

    private String formattedDate;

    private String weight;

    private String bodyMassIndex;

}
