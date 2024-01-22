package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiquidAndWeightAddingDto {

    @NotNull(message = "Поле \"liquid\" не должно отсутствовать")
    @Min(value = 0, message = "Количество потребляемой в день жидкости не может быть отрицательным")
    @Max(value = 16, message = "Количество потребляемой в день жидкости не может превышать 16 л")
    private Double liquid;

    @NotNull(message = "Поле \"weight\" не должно отсутствовать")
    @Min(value = 0, message = "Вес не может быть отрицательным")
    @Min(value = 250, message = "Вес не может превышать 250 кг")
    private Double weight;

}
