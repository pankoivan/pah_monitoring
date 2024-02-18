package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class PressureTablesDto implements OutDto {

    private Integer upper;

    private Integer lower;

    private String afterExercise;

}
