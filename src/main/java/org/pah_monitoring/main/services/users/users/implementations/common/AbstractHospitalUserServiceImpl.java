package org.pah_monitoring.main.services.users.users.implementations.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserAddingInformation;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
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
public abstract class AbstractHospitalUserServiceImpl<T extends HospitalUser, M extends HospitalUserAddingInformation, R, N> implements
        HospitalUserService<T, M, R, N> {

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

    protected T accessCheck(T requestedUser) throws NotEnoughRightsServiceException {

        switch (SecurityContextHolder.getContext().getAuthentication().getPrincipal()) {
            case MainAdministrator ignored -> {
                return requestedUser;
            }
            case HospitalUser hospitalUser -> {
                if (hospitalUser.getHospital().equals(requestedUser.getHospital())) {
                    return requestedUser;
                } else {
                    throw new NotEnoughRightsServiceException(
                            "Недостаточно прав для получения информации о пользователе с id \"%s\"".formatted(requestedUser.getId())
                    );
                }
            }
            default -> throw new NotEnoughRightsServiceException(
                    "Недостаточно прав для получения информации о пользователе с id \"%s\"".formatted(requestedUser.getId())
            );
        }

    }

    protected abstract Role getRole();

}
