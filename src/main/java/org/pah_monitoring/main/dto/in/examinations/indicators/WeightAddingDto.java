package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WeightAddingDto {

    @NotNull(message = "Поле \"weight\" не должно отсутствовать")
    @Min(value = 0, message = "Вес не может быть отрицательным")
    @Max(value = 250, message = "Вес не может превышать 250 кг")
    private Double weight;

}
