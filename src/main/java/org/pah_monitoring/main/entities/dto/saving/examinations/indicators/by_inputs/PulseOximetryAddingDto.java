package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PulseOximetryAddingDto {

    @NotNull(message = "Поле \"oxygenPercentage\" не должно отсутствовать")
    @Min(value = 0, message = "Процент кислорода в крови должен лежать в диапазоне от 0 до 100")
    @Min(value = 100, message = "Процент кислорода в крови должен лежать в диапазоне от 0 до 100")
    private Double oxygenPercentage;

    @NotNull(message = "Поле \"pulseRate\" не должно отсутствовать")
    @Min(value = 30, message = "Пульс должен лежать в диапазоне от 30 до 240 уд/мин")
    @Max(value = 240, message = "Пульс должен лежать в диапазоне от 30 до 240 уд/мин")
    private Integer pulseRate;

    @NotNull(message = "Поле \"duringExercise\" не должно отсутствовать")
    private Boolean duringExercise;

}
