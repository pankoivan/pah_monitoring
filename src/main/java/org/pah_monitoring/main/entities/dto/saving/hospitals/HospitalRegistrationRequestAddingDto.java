package org.pah_monitoring.main.entities.dto.saving.hospitals;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HospitalRegistrationRequestAddingDto {

    @Size(min = 2, max = 32, message = "Минимальная длина имени - 2 символа, максимальная - 32 символа")
    @NotNull(message = "Имя не должно отсутствовать")
    @NotEmpty(message = "Имя не должно быть пустым")
    @NotBlank(message = "Имя не должно состоять только из пробельных символов")
    private String name;

    @Size(min = 2, max = 64, message = "Минимальная длина фамилии - 2 символа, максимальная - 64 символа")
    @NotNull(message = "Фамилия не должна отсутствовать")
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @NotBlank(message = "Фамилия не должна состоять только из пробельных символов")
    private String lastname;

    @Size(max = 32, message = "Максимальная длина отчества - 32 символа")
    @NotNull(message = "Отчество должно присутствовать хотя бы в виде пустой строки")
    @NotBlank(message = "Отчество не должно состоять только из пробельных символов")
    private String patronymic;

    @Size(min = 4, max = 128, message = "Минимальная длина должности - 4 символа, максимальная - 128 символов")
    @NotNull(message = "Должность не должна отсутствовать")
    @NotEmpty(message = "Должность не должна быть пустой")
    @NotBlank(message = "Должность не должна состоять только из пробельных символов")
    private String post;

    @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Паспортные данные должны иметь формат \"XXXX XXXXXX\", где X - цифра от 0 до 9")
    @NotNull(message = "Паспортные данные не должны отсутствовать")
    private String passport;

    @Pattern(regexp = "^\\d{11}$", message = "Для номера телефона используйте только подряд идущие цифры от 0 до 9. Пример: 89112345129")
    @NotNull(message = "Номер телефона не должен отсутствовать")
    private String phoneNumber;

    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    @NotNull(message = "Почта не должна отсутствовать")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    private String email;

    @Size(max = 512, message = "Максимальная длина комментария - 512 символов")
    @NotNull(message = "Комментарий должен присутствовать хотя бы в виде пустой строки")
    private String comment;

    @Valid
    private HospitalAddingDto hospitalAddingDto;

}
