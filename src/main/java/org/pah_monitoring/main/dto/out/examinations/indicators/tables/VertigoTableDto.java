package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class VertigoTableDto implements OutDto {

    private String formattedDate;

    private String duration;

    private String nausea;

}
