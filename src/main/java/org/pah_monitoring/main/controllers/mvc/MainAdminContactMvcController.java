package org.pah_monitoring.main.controllers.mvc;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
public class MainAdminContactMvcController {

    private final MainAdminContactService service;

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("contacts", service.findAll());
        return "contacts";
    }

}
