package org.pah_monitoring.main.entities.dto.saving.users.users.editing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserEditingInformation;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorEditingDto extends DoctorSavingDto implements HospitalUserEditingInformation {

    Integer id;

}
