package org.pah_monitoring.main.dto.in.users.users.doctor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.pah_monitoring.main.dto.in.users.users.common.interfaces.HospitalUserEditingInfo;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DoctorEditingDto extends DoctorSavingDto implements HospitalUserEditingInfo {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    private Integer id;

}
