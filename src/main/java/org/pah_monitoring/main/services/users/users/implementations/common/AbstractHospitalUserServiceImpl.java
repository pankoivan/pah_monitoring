package org.pah_monitoring.main.services.users.users.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserEditingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserSavingInfo;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.services.auxiliary.auth.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractHospitalUserServiceImpl
        <T extends HospitalUser, M extends HospitalUserAddingInfo, R extends HospitalUserEditingInfo, N extends HospitalUserSavingInfo>
        implements HospitalUserService<T, M, R, N> {

    private RegistrationSecurityCodeService codeService;

    private AccessRightsCheckService checkService;

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
    public void checkAccessRightsForObtainingAll() throws NotEnoughRightsServiceException {
        if (!checkService.isMainAdministrator()) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForObtainingAllInHospital(Hospital requestedHospital) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isMainAdministrator() ||
                checkService.isHospitalUserFromSameHospital(requestedHospital)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForObtainingConcrete(T requestedHospitalUser) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isMainAdministrator() ||
                checkService.isHospitalUserFromSameHospital(requestedHospitalUser.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForEditing(T requestedEditingHospitalUser) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSameUser(requestedEditingHospitalUser) ||
                checkService.isAdministratorFromSameHospital(requestedEditingHospitalUser.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    protected abstract Role getRole();

}
