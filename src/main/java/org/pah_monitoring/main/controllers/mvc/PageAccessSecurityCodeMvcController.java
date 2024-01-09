package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/code-generation")
@PreAuthorize("permitAll()") // todo: change to hasAuthority('MAIN_ADMINISTRATOR')
public class PageAccessSecurityCodeMvcController {

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("expirationDateList", ExpirationDate.values());
        return "code-generation";
    }

}
