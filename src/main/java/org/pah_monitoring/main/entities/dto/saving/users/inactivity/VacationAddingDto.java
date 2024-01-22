package org.pah_monitoring.main.entities.dto.saving.users.inactivity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.inactivity.common.InactivityAddingDto;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class VacationAddingDto extends InactivityAddingDto {

    @NotNull(message = "Поле \"startDate\" не должно отсутствовать")
    private LocalDate startDate;

    @NotNull(message = "Поле \"endDate\" не должно отсутствовать")
    private LocalDate endDate;

}
