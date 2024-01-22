package org.pah_monitoring.main.entities.dto.saving.examinations.schedules;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationScheduleEditingDto extends ExaminationScheduleSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    Integer id;

}
