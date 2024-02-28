package org.pah_monitoring.main.dto.out.examinations.indicators.plain;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class OverallHealthPlainDto implements OutDto {

    private String breathlessness;

    private String fatigue;

    private String restFeeling;

    private String drowsiness;

    private String concentration;

    private String weakness;

    private String appetite;

    private String coldExtremities;

}
