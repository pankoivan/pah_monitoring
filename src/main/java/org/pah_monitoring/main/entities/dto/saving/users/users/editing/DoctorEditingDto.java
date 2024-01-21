package org.pah_monitoring.main.entities.dto.saving.users.users.editing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserEditingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorEditingDto extends DoctorSavingDto implements HospitalUserEditingInfo {

    @NotNull(message = "Идентификатор не должен отсутствовать")
    Integer id;

}
