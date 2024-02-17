package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;

@Data
public class PhysicalChangesAddingDto {

    @NotNull(message = "Поле \"Увеличение живота без переедания\" является обязательным")
    private Boolean abdominalEnlargement;

    @NotNull(message = "Поле \"Отёки ног\" является обязательным")
    private PhysicalChanges.LegsSwelling legsSwelling;

    @NotNull(message = "Поле \"«Сосудистые звёздочки» или «паутинки» на коже\" является обязательным")
    private Boolean vascularAsterisks;

    @NotNull(message = "Поле \"Цвет губ и кожи\" является обязательным")
    private PhysicalChanges.SkinColor skinColor;

    @NotNull(message = "Поле \"Утолщённые пальцы\" является обязательным")
    private Boolean fingersPhalanges;

    @NotNull(message = "Поле \"«Бочкообразная» грудная клетка\" является обязательным")
    private Boolean chest;

    @NotNull(message = "Поле \"Набухание шейных вен\" является обязательным")
    private Boolean neckVeins;

}
