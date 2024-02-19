package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class SpirometryTableDto implements OutDto {

    private String formattedDate;

    private String vlc;

    private String avlc;

    private String rlv;

    private String vfe1;

    private String tlc;

    private String tiffnoIndex;

}
