package org.pah_monitoring.main.controllers.mvc;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.entities.users.Doctor;
import org.pah_monitoring.main.entities.users.MainAdministrator;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/")
@PreAuthorize("permitAll()")
public class MainMvcController {

    private final MainAdminContactService mainAdminContactService;

    @GetMapping
    public String getPage(Model model, @AuthenticationPrincipal Object principal) {

        return switch (principal) {
            case MainAdministrator ignored -> "redirect:/hospitals";

            case Administrator administrator -> "redirect:/hospitals/%s"
                    .formatted(administrator.getEmployeeInformation().getHospital().getId());

            case Doctor doctor -> "redirect:/hospitals/%s/doctors/%s/patients"
                    .formatted(doctor.getEmployeeInformation().getHospital().getId(), doctor.getId());

            case Patient ignored -> "redirect:/indicators";

            default -> {
                model.addAttribute("contacts", mainAdminContactService.findAll());
                yield "main";
            }
        };

    }

}
