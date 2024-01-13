package org.pah_monitoring.main.entities.hospitals;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "hospital")
@Builder
@Entity
@Table(name = "hospital_registration_request")
public class HospitalRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 2, max = 32, message = "Минимальная длина имени - 2 символа, максимальная - 32 символа")
    @NotEmpty(message = "Имя не должно быть пустым")
    @NotBlank(message = "Имя не должно состоять только из пробельных символов")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 64, message = "Минимальная длина фамилии - 2 символа, максимальная - 64 символа")
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @NotBlank(message = "Фамилия не должна состоять только из пробельных символов")
    @Column(name = "lastname")
    private String lastname;

    @Size(max = 32, message = "Максимальная длина отчества - 32 символа")
    @Column(name = "patronymic")
    private String patronymic;

    @Size(min = 4, max = 128, message = "Минимальная длина должности - 4 символа, максимальная - 128 символов")
    @NotEmpty(message = "Должность не должна быть пустой")
    @NotBlank(message = "Должность не должна состоять только из пробельных символов")
    @Column(name = "post")
    private String post;

    @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Паспортные данные должны иметь формат \"XXXX XXXXXX\", где X - цифра от 0 до 9")
    @Column(name = "passport")
    private String passport;

    @Pattern(regexp = "^\\d{11}$", message = "Для номера телефона используйте только подряд идущие цифры от 0 до 9. Пример: 89112345129")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(min = 8, max = 256, message = "Минимальная длина почты - 8 символов, максимальная - 256 символов")
    @NotEmpty(message = "Почта не должна быть пустой")
    @NotBlank(message = "Почта не должна состоять только из пробельных символов")
    @Column(name = "email")
    private String email;

    @Size(max = 512, message = "Максимальная длина комментария - 512 символов")
    @Column(name = "comment")
    private String comment;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof HospitalRegistrationRequest other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
