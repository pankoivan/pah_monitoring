package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospital-registration")
@PreAuthorize("permitAll()") // todo: remove
public class HospitalRegistrationMvcController {

    private final HospitalRegistrationRequestService service;

    @GetMapping("/form") // todo: for all
    public String getForm() {
        return "hospitals/hospital-registration-form";
    }

    @GetMapping("/requests/{id}") // todo: only for main admin
    public String getRequest(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("request", service.findById(service.parsePathId(pathId)));
            model.addAttribute("role", Role.ADMINISTRATOR);
            model.addAttribute("expirationDateList", ExpirationDate.values());
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "hospitals/hospital-registration-request";
    }

}
