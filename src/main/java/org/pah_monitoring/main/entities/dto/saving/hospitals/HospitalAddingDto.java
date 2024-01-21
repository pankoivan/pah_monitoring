package org.pah_monitoring.main.entities.dto.saving.hospitals;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HospitalAddingDto {

    @Size(min = 12, max = 512, message = "Минимальная длина названия медицинского учреждения - 12 символов, максимальная - 512 символов")
    @NotNull(message = "Название медицинского учреждения не должно отсутствовать")
    @NotEmpty(message = "Название медицинского учреждения не должно быть пустым")
    @NotBlank(message = "Название медицинского учреждения не должно состоять только из пробельных символов")
    private String name;

}
