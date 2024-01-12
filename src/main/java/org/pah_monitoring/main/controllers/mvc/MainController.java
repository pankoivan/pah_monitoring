package org.pah_monitoring.main.controllers.mvc;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.users.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/")
@PreAuthorize("permitAll()")
public class MainController {

    private final MainAdminContactService mainAdminContactService;

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("contacts", mainAdminContactService.findAll());
        return "main";
    }

}
