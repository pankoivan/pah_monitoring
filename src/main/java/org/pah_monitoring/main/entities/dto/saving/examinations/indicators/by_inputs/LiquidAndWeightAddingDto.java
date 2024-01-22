package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiquidAndWeightAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Double liquid;

    @NotNull(message = "Поле не должно отсутствовать")
    private Double weight;

}
