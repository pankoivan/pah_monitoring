package org.pah_monitoring.main.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/code-generation")
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class AdminCodeGenMvcController {

    private final CurrentUserExtractionService extractionService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("admin", extractionService.administrator());
        model.addAttribute("expirationDateList", ExpirationDate.values());
        model.addAttribute("roles", Role.hospitalRoles());
        pageHeaderService.addHeader(model);
        return "admin-code-gen";
    }

}
