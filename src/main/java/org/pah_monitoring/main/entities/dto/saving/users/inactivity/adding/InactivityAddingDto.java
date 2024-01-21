package org.pah_monitoring.main.entities.dto.saving.users.inactivity.adding;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InactivityAddingDto {

    private Integer toWhomId;

    @Size(max = 1024, message = "Максимальная длина комментария - 1024 символа")
    private String comment;

}
