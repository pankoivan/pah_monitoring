package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.EventDuration;
import org.pah_monitoring.main.entities.main.examinations.indicators.ChestPain;

@Data
public class ChestPainAddingDto {

    @NotNull(message = "Поле \"Тип\" является обязательным")
    private ChestPain.Type type;

    @NotNull(message = "Поле \"Продолжительность\" является обязательным")
    private EventDuration duration;

    @NotNull(message = "Поле \"Помог ли приём нитроглицерина\" является обязательным")
    private ChestPain.Nitroglycerin nitroglycerin;

}
