package org.pah_monitoring.main.dto.in.users.users.editing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.dto.in.users.users.common.HospitalUserEditingInfo;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientEditingDto extends PatientSavingDto implements HospitalUserEditingInfo {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    Integer id;

}
