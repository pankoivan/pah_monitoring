package org.pah_monitoring.main.dto.out.examinations.indicators.tables;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.EventDuration;

@Data
@Builder
public class VertigoTableDto {

    private String formattedDate;

    private String duration;

    private String nausea;

}
