package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.WalkTest;

@Data
public class WalkTestAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean oxygenSupport;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean auxiliaryDevices;

    @NotNull(message = "Поле не должно отсутствовать")
    private Double distance;

    @NotNull(message = "Поле не должно отсутствовать")
    private Integer numberOfStops;

    @NotNull(message = "Поле не должно отсутствовать")
    private WalkTest.Breathlessness breathlessness;

    @Valid
    @NotNull(message = "Поле не должно отсутствовать")
    private PulseOximetryAddingDto pulseOximetryBefore;

    @Valid
    @NotNull(message = "Поле не должно отсутствовать")
    private PulseOximetryAddingDto pulseOximetryAfter;

    @Valid
    @NotNull(message = "Поле не должно отсутствовать")
    private PressureAddingDto pressureBefore;

    @Valid
    @NotNull(message = "Поле не должно отсутствовать")
    private PressureAddingDto pressureAfter;

}
