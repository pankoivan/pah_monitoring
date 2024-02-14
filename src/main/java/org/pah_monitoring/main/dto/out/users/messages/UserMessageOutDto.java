package org.pah_monitoring.main.dto.out.users.messages;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;

import java.time.LocalDateTime;

@Data
@Builder
public class UserMessageOutDto implements OutDto {

    private Integer id;

    private UserInformation author;

    private String authorFullName;

    private String recipient;

    private String text;

    private LocalDateTime date;

    private String formattedDate;

    private LocalDateTime editingDate;

    private String formattedEditingDate;

}
