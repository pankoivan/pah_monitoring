package org.pah_monitoring.main.services.users.users.implementations.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserEditingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserSavingInfo;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.users.users.common.HospitalUser;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

@NoArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractHospitalUserServiceImpl
        <T extends HospitalUser, M extends HospitalUserAddingInfo, R extends HospitalUserEditingInfo, N extends HospitalUserSavingInfo>
        implements HospitalUserService<T, M, R, N> {

    private RegistrationSecurityCodeService codeService;

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {

        RegistrationSecurityCode code;
        try {
            code = codeService.findByStringUuid(addingDto.getCode());
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }

        if (codeService.isExpired(code)) {
            throw new DataValidationServiceException(
                    "Истёк срок действия кода. Код был действителен до %s"
                            .formatted(DateTimeFormatConstants.DAY_MONTH_YEAR_WHITESPACE_HOUR_MINUTE_SECOND.format(code.getExpirationDate()))
            );
        }

        if (codeService.isNotSuitableForRole(code, getRole())) {
            throw new DataValidationServiceException("Код не предназначен для роли \"%s\"".formatted(getRole().getAlias()));
        }

        if (codeService.isNotSuitableForEmail(code, addingDto.getUserSecurityInformationAddingDto().getEmail())) {
            throw new DataValidationServiceException(
                    "Код не предназначен для почты \"%s\"".formatted(addingDto.getUserSecurityInformationAddingDto().getEmail())
            );
        }

    }

    @Override
    public void checkAccessForObtainingUser(T requestedUser) throws NotEnoughRightsServiceException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (
                (principal instanceof MainAdministrator) ||
                        ((principal instanceof HospitalUser hospitalUser && hospitalUser.getHospital().equals(requestedUser.getHospital())))
        ) {
            return;
        }

        throw new NotEnoughRightsServiceException(
                "Недостаточно прав для получения информации о пользователе с id \"%s\"".formatted(requestedUser.getId())
        );

    }

    @Override
    public void checkAccessForObtainingHospitalUsers(Hospital requestedHospital) throws NotEnoughRightsServiceException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (
                (principal instanceof MainAdministrator) ||
                        ((principal instanceof HospitalUser hospitalUser && hospitalUser.getHospital().equals(requestedHospital)))
        ) {
            return;
        }

        throw new NotEnoughRightsServiceException(
                "Недостаточно прав для получения информации о пользователях в медицинском учреждении с id \"%s\""
                        .formatted(requestedHospital.getId())
        );

    }

    @Override
    public void checkAccessForEditing(T requestedEditingUser) throws NotEnoughRightsServiceException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (
                ((principal instanceof Administrator administrator && administrator.getHospital().equals(requestedEditingUser.getHospital()))) ||
                        ((principal instanceof HospitalUser hospitalUser) && hospitalUser.getId().equals(requestedEditingUser.getId()))

        ) {
            return;
        }

        throw new NotEnoughRightsServiceException(
                "Недостаточно прав для редактирования пользователя с id \"%s\"".formatted(requestedEditingUser.getId())
        );

    }

    protected abstract Role getRole();

}
