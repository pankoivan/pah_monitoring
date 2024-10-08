package org.pah_monitoring.main.dto.out.hospitals;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class HospitalRegistrationRequestOutDto implements OutDto {

    private String email;

    private String hospitalName;

}
