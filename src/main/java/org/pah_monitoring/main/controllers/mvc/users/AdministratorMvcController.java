package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admins")
@PreAuthorize("permitAll()")
public class AdministratorMvcController {

    private final AdministratorService service;

    @GetMapping
    public String getAdmins(Model model) {
        model.addAttribute("admins", service.findAll());
        return "users/admins";
    }

}
