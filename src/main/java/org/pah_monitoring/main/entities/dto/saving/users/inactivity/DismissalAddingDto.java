package org.pah_monitoring.main.entities.dto.saving.users.inactivity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.inactivity.common.InactivityAddingDto;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class DismissalAddingDto extends InactivityAddingDto {

    @NotNull(message = "Дата не должна отсутствовать")
    private LocalDate date;

}
