package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.ExpirationDate;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.RedirectService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalRegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/hospital-registration")
public class HospitalRegistrationMvcController {

    private final HospitalRegistrationRequestService service;

    private final RedirectService redirectService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/form")
    @PreAuthorize("permitAll()")
    public String getFormPage(Model model) {
        if (redirectService.checkNotAnonymousUserRedirect()) {
            return redirectService.notAnonymousUserRedirect();
        }
        pageHeaderService.addHeader(model);
        return "hospitals/hospital-registration-form";
    }

    @GetMapping("/requests/{id}")
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getRequestPage(Model model, @PathVariable("id") String pathId) {
        try {
            HospitalRegistrationRequest request = service.findById(service.parsePathId(pathId));
            model.addAttribute("request", request);
            model.addAttribute("expirationDateList", ExpirationDate.values());
            model.addAttribute("role", Role.ADMINISTRATOR);
            pageHeaderService.addHeader(model);
            return "hospitals/hospital-registration-request";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
