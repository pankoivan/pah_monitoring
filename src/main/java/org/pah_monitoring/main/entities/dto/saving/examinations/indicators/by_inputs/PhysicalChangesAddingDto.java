package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PhysicalChangesAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean acrocyanosis;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean fingersPhalanges;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean nails;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean chest;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean neckVeins;

    @NotNull(message = "Поле не должно отсутствовать")
    private Boolean lowerExtremities;

}
