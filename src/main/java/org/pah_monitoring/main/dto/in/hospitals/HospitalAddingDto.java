package org.pah_monitoring.main.dto.in.hospitals;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HospitalAddingDto {

    @NotNull(message = "Поле \"name\" не должно отсутствовать")
    private String name;

    @NotNull(message = "Поле \"oid\" не должно отсутствовать")
    private String oid;

}
