package org.pah_monitoring.main.dto.out.examinations.indicators.plain;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class PhysicalChangesPlainDto implements OutDto {

    private String formattedDate;

    private String abdominalEnlargement;

    private String legsSwelling;

    private String vascularAsterisks;

    private String skinColor;

    private String fingersPhalanges;

    private String chest;

    private String neckVeins;

}
