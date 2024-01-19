package org.pah_monitoring.main.controllers.mvc.main_admin_contacts;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/contacts")
@PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
public class MainAdminContactMvcController {

    private final MainAdminContactService service;

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("contacts", service.findAll());
        return "contacts";
    }

}
