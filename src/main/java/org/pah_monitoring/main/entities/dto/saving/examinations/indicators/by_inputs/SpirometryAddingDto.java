package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpirometryAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Double vlc;

    @NotNull(message = "Поле не должно отсутствовать")
    private Double avlc;

    @NotNull(message = "Поле не должно отсутствовать")
    private Double rlv;

    @NotNull(message = "Поле не должно отсутствовать")
    private Double vfe1;

}
