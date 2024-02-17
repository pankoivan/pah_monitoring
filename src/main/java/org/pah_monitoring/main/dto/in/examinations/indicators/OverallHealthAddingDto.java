package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.OverallHealth;

@Data
public class OverallHealthAddingDto {

    @NotNull(message = "Поле \"Одышка\" является обязательным")
    private OverallHealth.Conditions breathlessness;

    @NotNull(message = "Поле \"Усталость\" является обязательным")
    private OverallHealth.Conditions fatigue;

    @NotNull(message = "Поле \"Отсутствие ощущения отдыха после полноценного ночного сна\" является обязательным")
    private Boolean restFeeling;

    @NotNull(message = "Поле \"Повышенная сонливость в дневное время суток\" является обязательным")
    private Boolean drowsiness;

    @NotNull(message = "Поле \"Снижение способности концентрироваться\" является обязательным")
    private Boolean concentration;

    @NotNull(message = "Поле \"Слабость\" является обязательным")
    private OverallHealth.Weakness weakness;

    @NotNull(message = "Поле \"Снижение аппетита\" является обязательным")
    private Boolean appetite;

    @NotNull(message = "Поле \"Холодные конечности\" является обязательным")
    private OverallHealth.ColdExtremities coldExtremities;

}
