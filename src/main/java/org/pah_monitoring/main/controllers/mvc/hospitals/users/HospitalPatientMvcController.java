package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals/{hospitalId}/patients")
@PreAuthorize("permitAll()") // todo: for main admin and people with hospital id = id
public class HospitalPatientMvcController {

    private final PatientService service;

    private final HospitalService hospitalService;

    @GetMapping
    public String getPatients(Model model, @PathVariable("hospitalId") String pathHospitalId) {
        try {
            model.addAttribute("patients", service.findAllByHospitalId(hospitalService.parsePathId(pathHospitalId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "users/patients";
    }

}
