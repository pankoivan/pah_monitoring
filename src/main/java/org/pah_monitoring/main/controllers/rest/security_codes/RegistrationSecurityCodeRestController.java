package org.pah_monitoring.main.controllers.rest.security_codes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.security_codes.RegistrationSecurityCodeByAdminAddingDto;
import org.pah_monitoring.main.dto.in.security_codes.RegistrationSecurityCodeByMainAdminAddingDto;
import org.pah_monitoring.main.dto.out.security_codes.RegistrationSecurityCodeOutDto;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/security-codes")
public class RegistrationSecurityCodeRestController {

    private final RegistrationSecurityCodeService service;

    @Qualifier("codeGeneratorByMainAdmin")
    private final RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByMainAdminAddingDto> codeGeneratorByMainAdmin;

    @Qualifier("codeGeneratorByAdmin")
    private final RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByAdminAddingDto> codeGeneratorByAdmin;

    @Qualifier("codeMapper")
    private final BaseEntityToOutDtoMapper<RegistrationSecurityCode, RegistrationSecurityCodeOutDto> codeMapper;

    private final HospitalService hospitalService;

    @PostMapping("/check")
    @PreAuthorize("permitAll()")
    public Map<String, Boolean> check(@RequestBody(required = false) String code) {
        return Collections.singletonMap("exists", service.existsByStringUuid(code));
    }

    @PostMapping("/generate/by-main-admin")
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public RegistrationSecurityCodeOutDto generateByMainAdmin(@RequestBody @Valid RegistrationSecurityCodeByMainAdminAddingDto addingDto,
                                                              BindingResult bindingResult) {
        try {
            codeGeneratorByMainAdmin.checkDataValidityForAdding(addingDto, bindingResult);
            RegistrationSecurityCode code = codeGeneratorByMainAdmin.generateAndSend(addingDto);
            hospitalService.upgrade(code.getHospital());
            return codeMapper.map(code);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/generate/by-admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RegistrationSecurityCodeOutDto generateByAdmin(@RequestBody @Valid RegistrationSecurityCodeByAdminAddingDto addingDto,
                                                          BindingResult bindingResult) {
        try {
            codeGeneratorByAdmin.checkDataValidityForAdding(addingDto, bindingResult);
            RegistrationSecurityCode code = codeGeneratorByAdmin.generateAndSend(addingDto);
            return codeMapper.map(code);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
