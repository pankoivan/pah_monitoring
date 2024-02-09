package org.pah_monitoring.main.dto.out.users.users.common;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class UserOutDto implements OutDto {

    private String fullName;

}
