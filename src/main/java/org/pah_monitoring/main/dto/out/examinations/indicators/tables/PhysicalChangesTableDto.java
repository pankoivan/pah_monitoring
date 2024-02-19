package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhysicalChangesTableDto {

    private String formattedDate;

    private String abdominalEnlargement;

    private String legsSwelling;

    private String vascularAsterisks;

    private String skinColor;

    private String fingersPhalanges;

    private String chest;

    private String neckVeins;

}
