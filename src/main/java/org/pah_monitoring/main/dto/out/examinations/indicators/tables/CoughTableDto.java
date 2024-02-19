package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoughTableDto {

    private String formattedDate;

    private String type;

    private String power;

    private String timbre;

    private String hemoptysis;

}
