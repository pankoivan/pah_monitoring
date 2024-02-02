package org.pah_monitoring.main.dto.out.users.inactivity;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class SickLeaveOutDto implements OutDto {

    private String formattedStartDate;

    private String formattedEndDate;

    private String comment;

}
