package org.pah_monitoring.main.dto.out.patient_additions;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;

@Data
@Builder
public class AnamnesisOutDto implements OutDto {

    private Boolean heartDisease;

    private Boolean lungDisease;

    private Boolean relativesDiseases;

    private Anamnesis.BloodClotting bloodClotting;

    private Boolean diabetes;

    private Integer height;

    private Double weight;

    private Double bodyMassIndex;

}
