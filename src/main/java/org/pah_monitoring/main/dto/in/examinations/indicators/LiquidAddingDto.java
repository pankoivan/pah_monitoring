package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiquidAddingDto {

    @NotNull(message = "Поле \"Количество жидкости в день\" является обязательным")
    @Min(value = 0, message = "Количество потребляемой в день жидкости не может быть отрицательным")
    @Max(value = 16, message = "Количество потребляемой в день жидкости не может превышать 16 л")
    private Double liquid;

}
