package org.pah_monitoring.main.entities.dto.saving.users.info;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.enums.Gender;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInformationSavingDto {

    private Integer id;

    @Size(min = 2, max = 32, message = "Минимальная длина имени - 2 символа, максимальная - 32 символа")
    @NotEmpty(message = "Имя не должно быть пустым")
    @NotBlank(message = "Имя не должно состоять только из пробельных символов")
    private String name;

    @Size(min = 2, max = 64, message = "Минимальная длина фамилии - 2 символа, максимальная - 64 символа")
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @NotBlank(message = "Фамилия не должна состоять только из пробельных символов")
    private String lastname;

    @Size(max = 32, message = "Максимальная длина отчества - 32 символа")
    private String patronymic;

    private Gender gender;

    private LocalDate birthdate;

    @Pattern(regexp = "^\\d{11}$", message = "Для номера телефона используйте только подряд идущие цифры от 0 до 9. Пример: 89112345129")
    private String phoneNumber;

}
