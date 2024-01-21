package org.pah_monitoring.main.entities.dto.saving.users.inactivity.adding;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class InactivePatientAddingDto extends InactivityAddingDto {

    private LocalDate date;

}
