package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Ascites;

@Data
public class AscitesAddingDto {

    @NotNull(message = "Поле не должно отсутствовать")
    private Ascites.LiquidAmount liquidAmount;

    @NotNull(message = "Поле не должно отсутствовать")
    private Ascites.ContentInfection contentInfection;

    @NotNull(message = "Поле не должно отсутствовать")
    private Ascites.ResponseToDrugTherapy responseToDrugTherapy;

}
