package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;

@Data
public class WalkTestAddingDto {

    @NotNull(message = "Поле \"Кислородная поддержка\" является обязательным")
    private Boolean oxygenSupport;

    @NotNull(message = "Поле \"Вспомогательные устройства\" является обязательным")
    private Boolean auxiliaryDevices;

    @NotNull(message = "Поле \"Расстояние\" является обязательным")
    @Min(value = 0, message = "Дистанция должна лежать в диапазоне от 0 до 1500 м")
    @Max(value = 1500, message = "Дистанция должна лежать в диапазоне от 0 до 1500 м")
    private Double distance;

    @NotNull(message = "Поле \"Процент кислорода\" является обязательным")
    @Min(value = 0, message = "Число остановок должно лежать в диапазоне от 0 до 60")
    @Max(value = 60, message = "Число остановок должно лежать в диапазоне от 0 до 60")
    private Integer numberOfStops;

    @NotNull(message = "Поле \"Одышка по шкале Борга\" является обязательным")
    private WalkTest.Breathlessness breathlessness;

    @Valid
    @NotNull(message = "Поле \"Пульсоксиметрия до нагрузки\" является обязательным")
    private PulseOximetryAddingDto pulseOximetryBefore;

    @Valid
    @NotNull(message = "Поле \"Пульсоксиметрия после нагрузки\" является обязательным")
    private PulseOximetryAddingDto pulseOximetryAfter;

    @Valid
    @NotNull(message = "Поле \"Давление до нагрузки\" является обязательным")
    private PressureAddingDto pressureBefore;

    @Valid
    @NotNull(message = "Поле \"Давление после нагрузки\" является обязательным")
    private PressureAddingDto pressureAfter;

}
