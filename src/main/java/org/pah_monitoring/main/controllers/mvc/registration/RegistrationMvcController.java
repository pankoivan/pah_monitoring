package org.pah_monitoring.main.controllers.mvc.registration;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.Gender;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.RedirectService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
@PreAuthorize("permitAll()")
public class RegistrationMvcController {

    private final RegistrationSecurityCodeService service;

    private final RedirectService redirectService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getRegistrationPage(Model model, @RequestParam(value = "code", required = false) String stringCode) {
        if (redirectService.checkNotAnonymousUserRedirect()) {
            return redirectService.notAnonymousUserRedirect();
        }
        try {
            RegistrationSecurityCode code = service.findByStringUuid(stringCode);
            model.addAttribute("code", code);
            model.addAttribute("genders", Gender.values());
            model.addAttribute(
                    "isEmployee",
                    code.getRole() == Role.ADMINISTRATOR || code.getRole() == Role.DOCTOR
            );
            pageHeaderService.addHeader(model);
            return "registration/registration";
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
