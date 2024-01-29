package org.pah_monitoring.main.entities.dto.saving.examinations.indicators;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpirometryAddingDto {

    // todo: more detailed

    @NotNull(message = "Поле \"vlc\" не должно отсутствовать")
    @Min(value = 800, message = "Жизненный объём лёгких должен лежать в диапазоне от 800 до 6200 л")
    @Min(value = 6200, message = "Жизненный объём лёгких должен лежать в диапазоне от 800 до 6200 л")
    private Double vlc;

    @NotNull(message = "Поле \"avlc\" не должно отсутствовать")
    private Double avlc;

    @NotNull(message = "Поле \"rlv\" не должно отсутствовать")
    @Min(value = 0, message = "Остаточный объём лёгких должен лежать в диапазоне от 0 до 1500 л")
    @Min(value = 1500, message = "Остаточный объём лёгких должен лежать в диапазоне от 0 до 1500 л")
    private Double rlv;

    @NotNull(message = "Поле \"vfe1\" не должно отсутствовать")
    private Double vfe1;

}
