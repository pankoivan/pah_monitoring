package org.pah_monitoring.main.dto.in.patient_additions;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;

@Data
public class AnamnesisAddingDto {

    @NotNull(message = "Поле \"Хронические заболевания сердца\" является обязательным")
    private Boolean heartDisease;

    @NotNull(message = "Поле \"Хронические заболевания лёгких\" является обязательным")
    private Boolean lungDisease;

    @NotNull(message = "Поле \"Хронические заболевания сердца или лёгких у родственников\" является обязательным")
    private Boolean relativesDiseases;

    @NotNull(message = "Поле \"Свёртываемость крови\" является обязательным")
    private Anamnesis.BloodClotting bloodClotting;

    @NotNull(message = "Поле \"Сахарный диабет\" является обязательным")
    private Boolean diabetes;

    @NotNull(message = "Поле \"Рост\" является обязательным")
    @Min(value = 40, message = "Рост не может быть меньше 40 см")
    @Max(value = 220, message = "Рост не может быть больше 220 см")
    private Integer height;

    @NotNull(message = "Поле \"Вес\" является обязательным")
    @Min(value = 0, message = "Вес не может быть отрицательным")
    @Max(value = 250, message = "Вес не может превышать 250 кг")
    private Double weight;

}
