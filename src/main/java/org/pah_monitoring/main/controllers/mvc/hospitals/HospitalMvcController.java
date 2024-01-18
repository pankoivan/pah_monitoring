package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals")
@PreAuthorize("permitAll()") // todo: remove
public class HospitalMvcController {

    private final HospitalService service;

    @GetMapping // todo: for main admin
    public String getHospitals(Model model) {
        model.addAttribute("hospitals", service.findAll());
        return "hospitals/hospitals";
    }

    @GetMapping("/{id}") // todo: for users with hospital id = request id
    public String getHospital(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("hospital", service.findByIdWithCurrentStateCheck(service.parsePathId(pathId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException | DataValidationServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "hospitals/hospital";
    }

}
