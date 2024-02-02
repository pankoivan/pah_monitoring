package org.pah_monitoring.main.dto.out.users.info;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class UserSecurityInformationOutDto implements OutDto {

    private String email;

}
