package org.pah_monitoring.main.dto.in.patient_additions;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnamnesisAddingDto {

    @NotNull(message = "Поле \"heartDisease\" не должно отсутствовать")
    private Boolean heartDisease;

    @NotNull(message = "Поле \"lungDisease\" не должно отсутствовать")
    private Boolean lungDisease;

    @NotNull(message = "Поле \"relativesDiseases\" не должно отсутствовать")
    private Boolean relativesDiseases;

    @NotNull(message = "Поле \"bloodClotting\" не должно отсутствовать")
    private Anamnesis.BloodClotting bloodClotting;

    @NotNull(message = "Поле \"diabetes\" не должно отсутствовать")
    private Boolean diabetes;

    @NotNull(message = "Поле \"height\" не должно отсутствовать")
    @Min(value = 40, message = "Рост не может быть меньше 40 см")
    @Max(value = 220, message = "Рост не может быть больше 220 см")
    private Integer height;

    @NotNull(message = "Поле \"weight\" не должно отсутствовать")
    @Min(value = 0, message = "Вес не может быть отрицательным")
    @Max(value = 250, message = "Вес не может превышать 250 кг")
    private Double weight;

}
