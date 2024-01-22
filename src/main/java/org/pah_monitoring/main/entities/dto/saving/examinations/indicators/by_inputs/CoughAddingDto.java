package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Cough;

@Data
public class CoughAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Cough.Type type;

    @NotNull(message = "Поле не должно отсутствовать")
    private Cough.Power power;

    @NotNull(message = "Поле не должно отсутствовать")
    private Cough.Timbre timbre;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean hemoptysis;

}
