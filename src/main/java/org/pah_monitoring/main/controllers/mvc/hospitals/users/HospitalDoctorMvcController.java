package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals/{hospitalId}/doctors")
@PreAuthorize("permitAll()") // todo: for main admin and people with hospital id = id
public class HospitalDoctorMvcController {

    private final DoctorService service;

    private final HospitalService hospitalService;

    @GetMapping
    public String getDoctors(Model model, @PathVariable("hospitalId") String pathHospitalId) {
        try {
            model.addAttribute("doctors", service.findAllByHospitalId(hospitalService.parsePathId(pathHospitalId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "users/doctors";
    }

}
