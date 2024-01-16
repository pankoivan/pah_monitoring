package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/hospitals")
@PreAuthorize("permitAll()")
public class HospitalMvcController {

    private HospitalService service;

    @GetMapping
    public String getHospitals(Model model) {
        model.addAttribute("hospitals", service.findAll());
        return "hospitals/hospitals";
    }

    @GetMapping("/{id}")
    public String getHospital(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("request", service.findById(service.parsePathId(pathId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "hospitals/hospital";
    }

}
