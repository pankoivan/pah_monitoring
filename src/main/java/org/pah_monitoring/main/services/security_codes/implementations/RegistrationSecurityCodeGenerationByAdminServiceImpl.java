package org.pah_monitoring.main.services.security_codes.implementations;

import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByAdminSavingDto;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.springframework.validation.BindingResult;

public class RegistrationSecurityCodeGenerationByAdminServiceImpl
        implements RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByAdminSavingDto> {

    @Override
    public RegistrationSecurityCode generate(RegistrationSecurityCodeByAdminSavingDto savingDto) {
        return null;
    }

    @Override
    public void checkDataValidityForSaving(RegistrationSecurityCodeByAdminSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

    }

}
