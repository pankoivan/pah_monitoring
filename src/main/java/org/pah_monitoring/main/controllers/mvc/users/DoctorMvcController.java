package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/doctors")
@PreAuthorize("permitAll()") // todo: only for main admin
public class DoctorMvcController {

    private final DoctorService service;

    @GetMapping
    public String getDoctors(Model model) {
        model.addAttribute("doctors", service.findAll());
        return "users/doctors";
    }

    @GetMapping("/{id}")
    public String getDoctor(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("doctor", service.findById(service.parsePathId(pathId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "users/profiles/doctor-profile";
    }

    @GetMapping("/{id}/patients")
    public String getDoctorPatients(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("doctorPatients", service.findById(service.parsePathId(pathId)).getPatients());
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "users/patients";
    }

}
