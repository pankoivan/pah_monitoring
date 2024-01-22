package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PulseOximetryAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Double oxygenPercentage;

    @NotNull(message = "Поле не должно отсутствовать")
    private Integer pulseRate;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean duringExercise;

}
