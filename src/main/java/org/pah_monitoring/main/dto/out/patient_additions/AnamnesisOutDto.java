package org.pah_monitoring.main.dto.out.patient_additions;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class AnamnesisOutDto implements OutDto {

    private Integer patientId;

    private String heartDisease;

    private String lungDisease;

    private String relativesDiseases;

    private String bloodClotting;

    private String diabetes;

    private Integer height;

    private Double weight;

    private Double bodyMassIndex;

}
