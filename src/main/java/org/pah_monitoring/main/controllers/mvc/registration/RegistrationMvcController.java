package org.pah_monitoring.main.controllers.mvc.registration;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
@PreAuthorize("permitAll()")
public class RegistrationMvcController {

    private final RegistrationSecurityCodeService service;

    @GetMapping
    public String getPage(@RequestParam(value = "code", required = false) String stringCode) {
        try {
            RegistrationSecurityCode code = service.findByStringUuid(stringCode);
            return switch (code.getRole()) {
                case ADMINISTRATOR -> "registration/admin-registration";
                case DOCTOR -> "registration/doctor-registration";
                case PATIENT -> "registration/patient-registration";
                default -> throw new UrlValidationMvcControllerException(
                        "Для роли \"%s\" не предусмотрена генерация кодов регистрации".formatted(code.getRole().getAlias())
                );
            };
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
