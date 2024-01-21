package org.pah_monitoring.main.entities.dto.saving.users.messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMessageAddingDto extends UserMessageSavingDto {

    @NotNull(message = "Идентификатор получателя не должен отсутствовать")
    Integer recipientId;

}
