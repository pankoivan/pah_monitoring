package org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.FunctionalClass;

@Data
public class FunctionalClassAddingDto {

    @NotNull(message = "Поле \"functionalClassNumber\" не должно отсутствовать")
    private FunctionalClass.FunctionalClassNumber functionalClassNumber;

}
