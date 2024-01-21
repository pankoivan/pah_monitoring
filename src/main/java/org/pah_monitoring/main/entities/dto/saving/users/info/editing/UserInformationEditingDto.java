package org.pah_monitoring.main.entities.dto.saving.users.info.editing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.UserInformationSavingDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInformationEditingDto extends UserInformationSavingDto {

    @NotNull(message = "Идентификатор не должен отсутствовать")
    Integer id;

}
