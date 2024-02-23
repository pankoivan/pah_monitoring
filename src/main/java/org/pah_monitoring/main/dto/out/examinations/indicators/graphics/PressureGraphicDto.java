package org.pah_monitoring.main.dto.out.examinations.indicators.graphics;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class PressureGraphicDto implements OutDto {

    private String formattedDate;

    private String upper;

    private String lower;

    private String afterExercise;

}
