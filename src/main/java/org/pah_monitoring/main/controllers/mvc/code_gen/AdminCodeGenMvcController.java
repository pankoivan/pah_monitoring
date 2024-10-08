package org.pah_monitoring.main.controllers.mvc.code_gen;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.ExpirationDate;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/code-generation")
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class AdminCodeGenMvcController {

    private final CurrentUserExtractionService extractionService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getCodeGenPage(Model model) {
        model.addAttribute("admin", extractionService.administrator());
        model.addAttribute("expirationDateList", ExpirationDate.values());
        model.addAttribute("roles", Role.hospitalRoles());
        pageHeaderService.addHeader(model);
        return "code_gen/admin-code-gen";
    }

}
