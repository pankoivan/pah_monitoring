package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pah_monitoring.main.entities.main.examinations.indicators.FunctionalClass;

@Data
public class FunctionalClassAddingDto {

    @NotNull(message = "Поле \"functionalClassNumber\" не должно отсутствовать")
    private FunctionalClass.FunctionalClassNumber functionalClassNumber;

}
