package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PressureAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Integer upper;

    @NotNull(message = "Поле не должно отсутствовать")
    private Integer lower;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean duringExercise;

}
