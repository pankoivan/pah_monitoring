package org.pah_monitoring.main.dto.in.users.users.patient;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.pah_monitoring.main.dto.in.users.users.common.interfaces.HospitalUserEditingInfo;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatientEditingDto extends PatientSavingDto implements HospitalUserEditingInfo {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    private Integer id;

}
