package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpirometryAddingDto {

    @NotNull(message = "Поле \"ЖЕЛ\" не должно отсутствовать")
    private Double vlc;

    @NotNull(message = "Поле \"ФЖЕЛ\" не должно отсутствовать")
    private Double avlc;

    @NotNull(message = "Поле \"ООЛ\" не должно отсутствовать")
    private Double rlv;

    @NotNull(message = "Поле \"ОФВ1\" не должно отсутствовать")
    private Double vfe1;

}
