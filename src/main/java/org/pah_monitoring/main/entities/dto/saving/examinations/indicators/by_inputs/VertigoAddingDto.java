package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.enums.EventDuration;

@Data
public class VertigoAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private EventDuration duration;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean nausea;

}
