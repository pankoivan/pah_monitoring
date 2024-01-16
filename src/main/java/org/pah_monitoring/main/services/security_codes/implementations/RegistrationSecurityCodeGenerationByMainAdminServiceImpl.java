package org.pah_monitoring.main.services.security_codes.implementations;

import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByMainAdminSavingDto;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.springframework.validation.BindingResult;

public class RegistrationSecurityCodeGenerationByMainAdminServiceImpl
        implements RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByMainAdminSavingDto> {

    @Override
    public RegistrationSecurityCode generate(RegistrationSecurityCodeByMainAdminSavingDto savingDto) {
        return null;
    }

    @Override
    public void checkDataValidityForSaving(RegistrationSecurityCodeByMainAdminSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

    }

}
