package org.pah_monitoring.main.controllers.rest.security_codes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByAdminSavingDto;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByMainAdminSavingDto;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@RestController
@RequestMapping("/rest/security-codes")
@PreAuthorize("permitAll()") // todo: remove
public class RegistrationSecurityCodeRestController {

    @Autowired
    private RegistrationSecurityCodeService service;

    @Autowired
    @Qualifier("codeGeneratorByMainAdmin")
    private RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByMainAdminSavingDto> codeGeneratorByMainAdmin;

    @Autowired
    @Qualifier("codeGeneratorByAdmin")
    private RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByAdminSavingDto> codeGeneratorByAdmin;

    @PostMapping("/check") // todo: for all
    public TrueFalseEntity isCodeExists(@RequestBody CheckCode checkCode) {
        return new TrueFalseEntity(service.existsByStringUuid(checkCode.code));
    }

    @PostMapping("/generate/by-main-admin") // todo: only for main admin
    public RegistrationSecurityCode generateByMainAdmin(@RequestBody @Valid RegistrationSecurityCodeByMainAdminSavingDto savingDto,
                                                        BindingResult bindingResult) {
        try {
            codeGeneratorByMainAdmin.checkDataValidityForSaving(savingDto, bindingResult);
            return codeGeneratorByMainAdmin.generate(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/generate/by-admin") // todo: only for admin
    public RegistrationSecurityCode generateByAdmin(@RequestBody @Valid RegistrationSecurityCodeByAdminSavingDto savingDto,
                                                        BindingResult bindingResult) {
        try {
            codeGeneratorByAdmin.checkDataValidityForSaving(savingDto, bindingResult);
            return codeGeneratorByAdmin.generate(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CheckCode {

        private String code;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class TrueFalseEntity {

        private boolean isTrue;

    }

}
