package org.pah_monitoring.main.entities.dto.saving.main_admin_contacts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainAdminContactSavingDto {

    private Integer id;

    @Size(min = 8, max = 80, message = "Минимальная длина контакта - 8 символов, максимальная - 80 символов")
    @NotEmpty(message = "Контакт не должен быть пустым")
    @NotBlank(message = "Контакт не должен состоять только из пробельных символов")
    private String contact;

    @Size(min = 2, max = 48, message = "Минимальная длина описания контакта - 2 символа, максимальная - 48 символов")
    @NotEmpty(message = "Описание контакта не должно быть пустым")
    @NotBlank(message = "Описание контакта не должно состоять только из пробельных символов")
    private String description;

}
