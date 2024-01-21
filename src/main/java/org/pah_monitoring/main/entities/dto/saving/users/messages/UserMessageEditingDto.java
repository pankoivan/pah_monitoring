package org.pah_monitoring.main.entities.dto.saving.users.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMessageEditingDto extends UserMessageSavingDto {

    Integer id;

}
