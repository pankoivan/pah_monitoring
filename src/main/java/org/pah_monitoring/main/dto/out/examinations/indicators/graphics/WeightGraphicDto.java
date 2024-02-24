package org.pah_monitoring.main.dto.out.examinations.indicators.graphics;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class WeightGraphicDto implements OutDto {

    private String formattedDate;

    private String weight;

    private String bodyMassIndex;

}
