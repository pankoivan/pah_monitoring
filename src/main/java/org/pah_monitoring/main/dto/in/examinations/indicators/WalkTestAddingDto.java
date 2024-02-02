package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.WalkTest;

@Data
public class WalkTestAddingDto {

    @NotNull(message = "Поле \"oxygenSupport\" не должно отсутствовать")
    private Boolean oxygenSupport;

    @NotNull(message = "Поле \"auxiliaryDevices\" не должно отсутствовать")
    private Boolean auxiliaryDevices;

    @NotNull(message = "Поле \"distance\" не должно отсутствовать")
    @Min(value = 0, message = "Дистанция должна лежать в диапазоне от 0 до 1500 м")
    @Min(value = 1500, message = "Дистанция должна лежать в диапазоне от 0 до 1500 м")
    private Double distance;

    @NotNull(message = "Поле \"numberOfStops\" не должно отсутствовать")
    @Min(value = 0, message = "Число остановок должно лежать в диапазоне от 0 до 60")
    @Min(value = 60, message = "Число остановок должно лежать в диапазоне от 0 до 60")
    private Integer numberOfStops;

    @NotNull(message = "Поле \"breathlessness\" не должно отсутствовать")
    private WalkTest.Breathlessness breathlessness;

    @Valid
    @NotNull(message = "Поле \"pulseOximetryBefore\" не должно отсутствовать")
    private PulseOximetryAddingDto pulseOximetryBefore;

    @Valid
    @NotNull(message = "Поле \"pulseOximetryAfter\" не должно отсутствовать")
    private PulseOximetryAddingDto pulseOximetryAfter;

    @Valid
    @NotNull(message = "Поле \"pressureBefore\" не должно отсутствовать")
    private PressureAddingDto pressureBefore;

    @Valid
    @NotNull(message = "Поле \"pressureAfter\" не должно отсутствовать")
    private PressureAddingDto pressureAfter;

}
