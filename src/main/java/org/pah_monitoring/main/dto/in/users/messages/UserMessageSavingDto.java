package org.pah_monitoring.main.dto.in.users.messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class UserMessageSavingDto {

    @NotNull(message = "Поле \"text\" не должно отсутствовать")
    @NotEmpty(message = "Сообщение не должно быть пустым")
    @NotBlank(message = "Сообщение не должно состоять только из пробельных символов")
    @Size(max = 1024, message = "Максимальная длина сообщения - 1024 символа")
    private String text;

}
