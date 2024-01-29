package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.enums.EventDuration;
import org.pah_monitoring.main.entities.examinations.indicators.ChestPain;

@Data
public class ChestPainAddingDto {

    @NotNull(message = "Поле \"type\" не должно отсутствовать")
    private ChestPain.Type type;

    @NotNull(message = "Поле \"duration\" не должно отсутствовать")
    private EventDuration duration;

    @NotNull(message = "Поле \"nitroglycerin\" не должно отсутствовать")
    private ChestPain.Nitroglycerin nitroglycerin;

}
