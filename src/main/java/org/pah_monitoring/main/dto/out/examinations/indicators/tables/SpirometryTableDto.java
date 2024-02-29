package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class SpirometryTableDto implements OutDto {

    private String formattedDate;

    private Double vlc;

    private Double avlc;

    private Double rlv;

    private Double vfe1;

    private Double tlc;

    private Double tiffnoIndex;

}
