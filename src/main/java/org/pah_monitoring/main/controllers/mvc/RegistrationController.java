package org.pah_monitoring.main.controllers.mvc;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/registration")
@PreAuthorize("permitAll()")
public class RegistrationController {

    private final RegistrationSecurityCodeRepository repository;

    /*public String getPage(@RequestParam(value = "code", required = false) String stringCode) {
        if (stringCode == null || !repository.existsByCode(UUID.fromString(stringCode))) {
            throw new UrlValidationMvcControllerException("Что-то не так");
        }
        RegistrationSecurityCode code = repository.findByCode(stringCode);
        if (code.getRole() == Role.ADMINISTRATOR) {
            return "registration-administrator";
        }
        if (code.getRole() == Role.DOCTOR) {
            return "registration-doctor";
        }
        if (code.getRole() == Role.PATIENT) {
            return "registration-patient";
        }
        throw new UrlValidationMvcControllerException("Что-то не так");
    }*/

}
