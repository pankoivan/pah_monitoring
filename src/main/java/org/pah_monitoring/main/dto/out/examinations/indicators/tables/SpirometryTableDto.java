package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpirometryTableDto {

    private String formattedDate;

    private String vlc;

    private String avlc;

    private String rlv;

    private String vfe1;

}
