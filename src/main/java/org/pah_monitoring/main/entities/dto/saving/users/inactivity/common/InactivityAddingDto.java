package org.pah_monitoring.main.entities.dto.saving.users.inactivity.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InactivityAddingDto {

    @NotNull(message = "Идентификатор адресата не должен отсутствовать")
    private Integer toWhomId;

    @Size(max = 1024, message = "Максимальная длина комментария - 1024 символа")
    @NotNull(message = "Комментарий должен присутствовать хотя бы в виде пустой строки")
    @NotBlank(message = "Комментарий не должен состоять только из пробельных символов")
    private String comment;

}
