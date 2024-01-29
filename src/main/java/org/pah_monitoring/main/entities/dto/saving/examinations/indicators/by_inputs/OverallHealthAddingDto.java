package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.examinations.indicators.OverallHealth;

@Data
public class OverallHealthAddingDto {

    @NotNull(message = "Поле \"fatigue\" не должно отсутствовать")
    private Boolean fatigue;

    @NotNull(message = "Поле \"restFeeling\" не должно отсутствовать")
    private Boolean restFeeling;

    @NotNull(message = "Поле \"drowsiness\" не должно отсутствовать")
    private Boolean drowsiness;

    @NotNull(message = "Поле \"concentration\" не должно отсутствовать")
    private Boolean concentration;

    @NotNull(message = "Поле \"weakness\" не должно отсутствовать")
    private OverallHealth.Weakness weakness;

    @NotNull(message = "Поле \"appetite\" не должно отсутствовать")
    private Boolean appetite;

    @NotNull(message = "Поле \"coldExtremities\" не должно отсутствовать")
    private OverallHealth.ColdExtremities coldExtremities;

}
