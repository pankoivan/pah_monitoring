package org.pah_monitoring.main.entities.dto.saving.users.inactivity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.inactivity.common.InactivityAddingDto;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class SickLeaveAddingDto extends InactivityAddingDto {

    @NotNull(message = "Дата начала не должна отсутствовать")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания не должна отсутствовать")
    private LocalDate endDate;

}
