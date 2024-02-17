package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.Cough;

@Data
public class CoughAddingDto {

    @NotNull(message = "Поле \"Тип\" является обязательным")
    private Cough.Type type;

    @NotNull(message = "Поле \"Сила\" является обязательным")
    private Cough.Power power;

    @NotNull(message = "Поле \"Тембр\" является обязательным")
    private Cough.Timbre timbre;

    @NotNull(message = "Поле \"Кровохарканье\" является обязательным")
    private Boolean hemoptysis;

}
