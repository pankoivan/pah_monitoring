package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PressureAddingDto {

    @NotNull(message = "Поле \"upper\" не должно отсутствовать")
    @Min(value = 50, message = "Верхнее давление должно лежать в диапазоне от 50 до 260 мм рт. ст.")
    @Max(value = 260, message = "Верхнее давление должно лежать в диапазоне от 50 до 260 мм рт. ст.")
    private Integer upper;

    @NotNull(message = "Поле \"lower\" не должно отсутствовать")
    @Min(value = 30, message = "Нижнее давление должно лежать в диапазоне от 30 до 160 мм рт. ст.")
    @Max(value = 160, message = "Нижнее давление должно лежать в диапазоне от 30 до 160 мм рт. ст.")
    private Integer lower;

    @NotNull(message = "Поле \"duringExercise\" не должно отсутствовать")
    private Boolean duringExercise;

}
