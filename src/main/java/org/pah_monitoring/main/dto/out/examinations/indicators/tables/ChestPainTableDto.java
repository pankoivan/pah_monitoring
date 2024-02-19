package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChestPainTableDto {

    private String formattedDate;

    private String type;

    private String duration;

    private String nitroglycerin;

}
