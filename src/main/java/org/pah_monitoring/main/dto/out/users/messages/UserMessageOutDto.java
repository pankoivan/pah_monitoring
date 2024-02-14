package org.pah_monitoring.main.dto.out.users.messages;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class UserMessageOutDto implements OutDto {

    private Integer id;

    private String author;

    private String recipient;

    private String text;

    private String formattedDate;

    private String formattedEditingDate;

}
