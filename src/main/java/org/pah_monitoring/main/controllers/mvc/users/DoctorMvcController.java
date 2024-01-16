package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
