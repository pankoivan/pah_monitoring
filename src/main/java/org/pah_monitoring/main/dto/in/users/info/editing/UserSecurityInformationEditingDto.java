package org.pah_monitoring.main.dto.in.users.info.editing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.dto.in.users.info.saving.UserSecurityInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSecurityInformationEditingDto extends UserSecurityInformationSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    Integer id;

    @Size(min = 3, max = 63, message = "Минимальная длина пароля - 3 символа, максимальная - 63 символа")
    @NotEmpty(message = "Пароль не должен быть пустым")
    @NotBlank(message = "Пароль не должен состоять только из пробельных символов")
    private String password;

}
