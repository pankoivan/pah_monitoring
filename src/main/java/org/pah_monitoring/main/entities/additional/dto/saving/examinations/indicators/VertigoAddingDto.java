package org.pah_monitoring.main.entities.additional.dto.saving.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.EventDuration;

@Data
public class VertigoAddingDto {

    @NotNull(message = "Поле \"duration\" не должно отсутствовать")
    private EventDuration duration;

    @NotNull(message = "Поле \"nausea\" не должно отсутствовать")
    private Boolean nausea;

}
