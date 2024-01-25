package org.pah_monitoring.main.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.services.auxiliary.redirect.interfaces.RedirectService;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final RedirectService redirectService;

    @GetMapping
    public String getPage(Model model) {
        if (redirectService.checkMainRedirect()) {
            return redirectService.mainRedirect();
        }
        model.addAttribute("contacts", mainAdminContactService.findAll());
        return "main";
    }

}
