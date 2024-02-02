package org.pah_monitoring.main.dto.out.users.inactivity;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class DismissalOutDto implements OutDto {

    private String formattedDate;

    private String comment;

}
