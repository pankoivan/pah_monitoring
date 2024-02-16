package org.pah_monitoring.main.controllers.mvc.main_admin_contacts;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.main.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
@PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
public class MainAdminContactMvcController {

    private final MainAdminContactService service;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getMainAdminContactsPage(Model model) {
        model.addAttribute("contacts", service.findAll());
        pageHeaderService.addHeader(model);
        return "main_admin_contacts/contacts";
    }

}
