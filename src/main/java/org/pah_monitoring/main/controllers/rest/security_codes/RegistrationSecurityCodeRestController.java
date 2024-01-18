package org.pah_monitoring.main.controllers.rest.security_codes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByAdminAddingDto;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByMainAdminAddingDto;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.email.interfaces.EmailService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/security-codes")
public class RegistrationSecurityCodeRestController {

    private final RegistrationSecurityCodeService service;

    @Qualifier("codeGeneratorByMainAdmin")
    private final RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByMainAdminAddingDto> codeGeneratorByMainAdmin;

    @Qualifier("codeGeneratorByAdmin")
    private final RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByAdminAddingDto> codeGeneratorByAdmin;

    @Qualifier("codeEmailSender")
    private final EmailService<RegistrationSecurityCode> emailMessageSender;

    private final HospitalService hospitalService;

    @PostMapping("/check")
    @PreAuthorize("permitAll()")
    public TrueFalseResponse check(@RequestBody CodeCheckRequest codeCheckRequest) {
        return new TrueFalseResponse(service.existsByStringUuid(codeCheckRequest.code));
    }

    @PostMapping("/generate/by-main-admin")
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public RegistrationSecurityCode generateByMainAdmin(@RequestBody @Valid RegistrationSecurityCodeByMainAdminAddingDto addingDto,
                                                        BindingResult bindingResult) {
        try {
            codeGeneratorByMainAdmin.checkDataValidityForSaving(addingDto, bindingResult);
            RegistrationSecurityCode code = codeGeneratorByMainAdmin.add(addingDto);
            emailMessageSender.send(code.getEmail(), code, false);
            hospitalService.upgrade(code.getHospital());
            return code;
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/generate/by-admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RegistrationSecurityCode generateByAdmin(@RequestBody @Valid RegistrationSecurityCodeByAdminAddingDto savingDto,
                                                    BindingResult bindingResult) {
        try {
            codeGeneratorByAdmin.checkDataValidityForSaving(savingDto, bindingResult);
            RegistrationSecurityCode code = codeGeneratorByAdmin.add(savingDto);
            emailMessageSender.send(code.getEmail(), code, false);
            return code;
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class CodeCheckRequest {
        private String code;
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class TrueFalseResponse {
        private boolean isTrue;
    }

}
