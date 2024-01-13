package org.pah_monitoring.main.controllers.mvc;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.utils.UuidUtils;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/registration")
@PreAuthorize("permitAll()")
public class RegistrationController {

    private final RegistrationSecurityCodeService service;

    public String getPage(@RequestParam(value = "code", required = false) String stringCode) {
        RegistrationSecurityCode code;
        try {
            code = service.findByCode(UuidUtils.fromString(stringCode));
        } catch (DataSearchingServiceException | UuidUtilsException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return switch (code.getRole()) {
            case ADMINISTRATOR -> "registration-administrator";
            case DOCTOR -> "registration-doctor";
            case PATIENT -> "registration-patient";
            default -> throw new UrlValidationMvcControllerException();
        };
    }

}
