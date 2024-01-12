package org.pah_monitoring.main.entities.main_admin_contacts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "main_admin_contact")
public class MainAdminContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 8, max = 80, message = "Минимальная длина контакта - 8 символов, максимальная - 80 символов")
    @NotEmpty(message = "Контакт не должен быть пустым")
    @NotBlank(message = "Контакт не должен состоять только из пробельных символов")
    @Column(name = "contact")
    private String contact;

    @Size(min = 2, max = 48, message = "Минимальная длина описания контакта - 2 символа, максимальная - 48 символов")
    @NotEmpty(message = "Описание контакта не должно быть пустым")
    @NotBlank(message = "Описание контакта не должно состоять только из пробельных символов")
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof MainAdminContact other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
