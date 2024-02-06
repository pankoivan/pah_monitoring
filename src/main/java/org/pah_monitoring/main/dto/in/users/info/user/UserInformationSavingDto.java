package org.pah_monitoring.main.dto.in.users.info.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.pah_monitoring.main.entities.main.enums.Gender;

import java.time.LocalDate;

@Data
public abstract class UserInformationSavingDto {

    @NotNull(message = "Поле \"name\" не должно отсутствовать")
    @NotEmpty(message = "Имя не должно быть пустым")
    @NotBlank(message = "Имя не должно состоять только из пробельных символов")
    @Size(min = 2, max = 32, message = "Минимальная длина имени - 2 символа, максимальная - 32 символа")
    private String name;

    @NotNull(message = "Поле \"lastname\" не должно отсутствовать")
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @NotBlank(message = "Фамилия не должна состоять только из пробельных символов")
    @Size(min = 2, max = 64, message = "Минимальная длина фамилии - 2 символа, максимальная - 64 символа")
    private String lastname;

    @Size(max = 32, message = "Максимальная длина отчества - 32 символа")
    private String patronymic;

    private Gender gender;

    private LocalDate birthdate;

    @NotNull(message = "Поле \"phoneNumber\" не должно отсутствовать")
    @Pattern(regexp = "^\\d{11}$", message = "Для номера телефона используйте только подряд идущие цифры от 0 до 9. Пример: 89112345129")
    private String phoneNumber;

}
