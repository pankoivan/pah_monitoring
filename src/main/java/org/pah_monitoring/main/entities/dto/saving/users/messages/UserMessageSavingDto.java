package org.pah_monitoring.main.entities.dto.saving.users.messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserMessageSavingDto {

    @Size(max = 1024, message = "Максимальная длина сообщения - 1024 символа")
    @NotEmpty(message = "Сообщение не должно быть пустым")
    @NotBlank(message = "Сообщение не должно состоять только из пробельных символов")
    @NotNull(message = "Сообщение не должно отсутствовать")
    private String text;

}
