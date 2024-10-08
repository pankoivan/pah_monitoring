package org.pah_monitoring.main.mappers.users.info;

import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.users.info.UserInformationOutDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("userInformationMapper")
public class UserInformationToOutDtoMapper implements BaseEntityToOutDtoMapper<UserInformation, UserInformationOutDto> {

    @Override
    @NullWhenNull
    public UserInformationOutDto map(UserInformation userInformation) {
        return UserInformationOutDto
                .builder()
                .name(userInformation.getName())
                .lastname(userInformation.getLastname())
                .patronymic(userInformation.getPatronymic())
                .fullName(userInformation.getFullName())
                .phoneNumber(userInformation.getPhoneNumber())
                .sourcePhoneNumber(PhoneNumberUtils.toSource(userInformation.getPhoneNumber()))
                .gender(userInformation.getGender())
                .genderAlias(userInformation.getGender() != null ? userInformation.getGender().getAlias() : null)
                .birthdate(userInformation.getBirthdate())
                .formattedBirthdate(userInformation.getFormattedBirthdate())
                .build();
    }

}
