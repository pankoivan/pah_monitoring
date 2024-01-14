package org.pah_monitoring.main.entities.dto.saving.users.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.enums.Gender;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInformationSavingDto {

    // todo: hibernate validation

    private Integer id;

    private String name;

    private String lastname;

    private String patronymic;

    private Gender gender;

    private LocalDate birthdate;

    private String phoneNumber;

}
