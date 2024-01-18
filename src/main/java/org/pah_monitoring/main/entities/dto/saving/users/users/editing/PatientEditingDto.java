package org.pah_monitoring.main.entities.dto.saving.users.users.editing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientEditingDto extends PatientSavingDto {

    Integer id;

}
