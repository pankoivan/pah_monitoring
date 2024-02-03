package org.pah_monitoring.main.dto.out.users.inactivity.common;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class InactivityOutDto implements OutDto {

    private String inactivityMessage;

    private String authorMessagePart;

    private String authorFullName;

    private Integer authorUserInformationId;

    private String comment;

}
