package org.pah_monitoring.main.entities.dto.saving.users.inactivity.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InactivityAddingDto {

    @NotNull(message = "Поле \"toWhomId\" не должно отсутствовать")
    private Integer toWhomId;

    @Size(max = 1024, message = "Максимальная длина комментария - 1024 символа")
    @NotNull(message = "Поле \"comment\" не должно отсутствовать")
    private String comment;

}
