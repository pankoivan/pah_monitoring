package org.pah_monitoring.main.dto.in.users.messages;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserMessageEditingDto extends UserMessageSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    private Integer id;

}
