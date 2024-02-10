package org.pah_monitoring.main.dto.in.examinations.schedules;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExaminationScheduleEditingDto extends ExaminationScheduleSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    private Integer id;

}
