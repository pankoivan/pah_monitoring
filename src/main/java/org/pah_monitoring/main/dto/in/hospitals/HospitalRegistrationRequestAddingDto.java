package org.pah_monitoring.main.dto.in.hospitals;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HospitalRegistrationRequestAddingDto {

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

    @NotNull(message = "Поле \"post\" не должно отсутствовать")
    @NotEmpty(message = "Должность не должна быть пустой")
    @NotBlank(message = "Должность не должна состоять только из пробельных символов")
    @Size(min = 4, max = 128, message = "Минимальная длина должности - 4 символа, максимальная - 128 символов")
    private String post;

    @NotNull(message = "Поле \"passport\" не должно отсутствовать")
    @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Паспортные данные должны иметь формат \"XXXX XXXXXX\", где X - цифра от 0 до 9")
    private String passport;

    @NotNull(message = "Поле \"phoneNumber\" не должно отсутствовать")
    @Pattern(regexp = "^\\d{11}$", message = "Для номера телефона используйте только подряд идущие цифры от 0 до 9. Пример: 89112345129")
    private String phoneNumber;

    @NotNull(message = "Поле \"email\" не должно отсутствовать")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    private String email;

    @Size(max = 512, message = "Максимальная длина комментария - 512 символов")
    private String comment;

    @Valid
    @NotNull(message = "Поле \"hospitalAddingDto\" не должно отсутствовать")
    private HospitalAddingDto hospitalAddingDto;

}
