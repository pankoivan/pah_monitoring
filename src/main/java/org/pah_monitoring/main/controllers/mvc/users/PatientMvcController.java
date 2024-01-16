package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/patients")
@PreAuthorize("permitAll()")
public class PatientMvcController {

    private final PatientService service;

    @GetMapping
    public String getPatients(Model model) {
        model.addAttribute("patients", service.findAll());
        return "users/patients";
    }

}
