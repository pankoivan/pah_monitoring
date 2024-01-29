package org.pah_monitoring.main.entities.dto.saving.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PhysicalChangesAddingDto {

    @NotNull(message = "Поле \"acrocyanosis\" не должно отсутствовать")
    private Boolean acrocyanosis;

    @NotNull(message = "Поле \"fingersPhalanges\" не должно отсутствовать")
    private Boolean fingersPhalanges;

    @NotNull(message = "Поле \"nails\" не должно отсутствовать")
    private Boolean nails;

    @NotNull(message = "Поле \"chest\" не должно отсутствовать")
    private Boolean chest;

    @NotNull(message = "Поле \"neckVeins\" не должно отсутствовать")
    private Boolean neckVeins;

    @NotNull(message = "Поле \"lowerExtremities\" не должно отсутствовать")
    private Boolean lowerExtremities;

}
