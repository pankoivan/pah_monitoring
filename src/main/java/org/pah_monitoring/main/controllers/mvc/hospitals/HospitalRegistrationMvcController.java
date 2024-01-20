package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
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
public class HospitalRegistrationMvcController {

    private final HospitalRegistrationRequestService service;

    @GetMapping("/form")
    @PreAuthorize("permitAll()")
    public String getForm() {
        return "hospitals/hospital-registration-form";
    }

    @GetMapping("/requests/{id}")
    public String getRequest(Model model, @PathVariable("id") String pathId) {
        try {
            HospitalRegistrationRequest request = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(request);
            model.addAttribute("request", request);
            model.addAttribute("role", Role.ADMINISTRATOR);
            model.addAttribute("expirationDateList", ExpirationDate.values());
            return "hospitals/hospital-registration-request";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
