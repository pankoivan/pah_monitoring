package org.pah_monitoring.main.dto.out.users.info;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.entities.main.enums.Gender;

import java.time.LocalDate;

@Data
@Builder
public class UserInformationOutDto implements OutDto {

    private String name;

    private String lastname;

    private String patronymic;

    private String fullName;

    private String phoneNumber;

    private String sourcePhoneNumber;

    private Gender gender;

    private String genderAlias;

    private LocalDate birthdate;

    private String formattedBirthdate;

}
