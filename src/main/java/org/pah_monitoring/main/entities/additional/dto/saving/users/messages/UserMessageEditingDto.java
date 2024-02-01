package org.pah_monitoring.main.entities.additional.dto.saving.users.messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMessageEditingDto extends UserMessageSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    Integer id;

}
