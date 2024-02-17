package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.EventDuration;

@Data
public class FaintingAddingDto {

    @NotNull(message = "Поле \"Продолжительность\" является обязательным")
    private EventDuration duration;

    @NotNull(message = "Поле \"Во время упражнения\" является обязательным")
    private Boolean duringExercise;

}
