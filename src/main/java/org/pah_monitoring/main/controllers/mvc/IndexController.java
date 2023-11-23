package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String returnIndexPage(Model model, Authentication authentication) {
        try {
            System.out.println(AuthenticationUtils.extractCurrentUser(authentication, MainAdministrator.class));
        } catch (AuthenticationUtilsException e) {
            System.out.println(e.getMessage());
        }
        return "index";
    }

}
