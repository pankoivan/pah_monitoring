package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;

@Data
public class PhysicalChangesAddingDto {

    @NotNull(message = "Поле \"abdominalEnlargement\" не должно отсутствовать")
    private Boolean abdominalEnlargement;

    @NotNull(message = "Поле \"legsSwelling\" не должно отсутствовать")
    private PhysicalChanges.LegsSwelling legsSwelling;

    @NotNull(message = "Поле \"vascularAsterisks\" не должно отсутствовать")
    private Boolean vascularAsterisks;

    @NotNull(message = "Поле \"skinColor\" не должно отсутствовать")
    private PhysicalChanges.SkinColor skinColor;

    @NotNull(message = "Поле \"acrocyanosis\" не должно отсутствовать")
    private Boolean acrocyanosis;

    @NotNull(message = "Поле \"fingersPhalanges\" не должно отсутствовать")
    private Boolean fingersPhalanges;

    @NotNull(message = "Поле \"chest\" не должно отсутствовать")
    private Boolean chest;

    @NotNull(message = "Поле \"neckVeins\" не должно отсутствовать")
    private Boolean neckVeins;

}
