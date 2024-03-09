package org.pah_monitoring.main.dto.in.users.inactivity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.pah_monitoring.main.dto.in.users.inactivity.common.InactivityAddingDto;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VacationAddingDto extends InactivityAddingDto {

    @NotNull(message = "Поле \"Дата окончания\" является обязательным")
    private LocalDate endDate;

}
