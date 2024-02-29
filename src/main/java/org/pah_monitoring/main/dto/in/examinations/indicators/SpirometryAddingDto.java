package org.pah_monitoring.main.dto.in.examinations.indicators;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpirometryAddingDto {

    @NotNull(message = "Поле \"ЖЕЛ\" является обязательным")
    @Min(value = 0, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    @Max(value = 12, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    private Double vlc;

    @NotNull(message = "Поле \"ФЖЕЛ\" является обязательным")
    @Min(value = 0, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    @Max(value = 12, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    private Double avlc;

    @NotNull(message = "Поле \"ООЛ\" является обязательным")
    @Min(value = 0, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    @Max(value = 12, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    private Double rlv;

    @NotNull(message = "Поле \"ОФВ1\" является обязательным")
    @Min(value = 0, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    @Max(value = 12, message = "Все указываемые объёмы должны лежать в диапазоне от 0 до 12 л")
    private Double vfe1;

}
