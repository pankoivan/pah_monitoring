package org.pah_monitoring.main.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
@PreAuthorize("permitAll()")
public class MainMvcController {

    private final MainAdminContactService mainAdminContactService;

    @GetMapping
    public String getPage(Model model, @AuthenticationPrincipal Object principal) {

        return switch (principal) {
            case MainAdministrator ignored -> "redirect:/hospitals";

            case Administrator administrator -> "redirect:/hospitals/%s".formatted(administrator.getHospital().getId());

            case Doctor doctor -> "redirect:/doctors/%s/patients".formatted(doctor.getId());

            case Patient ignored -> "redirect:/indicators";

            default -> {
                model.addAttribute("contacts", mainAdminContactService.findAll());
                yield "main";
            }
        };

    }

}
