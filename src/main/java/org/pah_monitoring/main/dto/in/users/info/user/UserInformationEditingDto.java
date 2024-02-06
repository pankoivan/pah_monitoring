package org.pah_monitoring.main.dto.in.users.info.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserInformationEditingDto extends UserInformationSavingDto {

    @NotNull(message = "Поле \"id\" не должно отсутствовать")
    private Integer id;

}
