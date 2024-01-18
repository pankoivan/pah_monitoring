package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/patients")
@PreAuthorize("permitAll()")  // todo: only for main admin
public class PatientMvcController {

    private final PatientService service;

    @GetMapping
    public String getPatients(Model model) {
        model.addAttribute("patients", service.findAll());
        return "users/patients";
    }

    @GetMapping("/{id}")
    public String getPatient(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("patient", service.findById(service.parsePathId(pathId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "users/profiles/patient-profile";
    }

}
