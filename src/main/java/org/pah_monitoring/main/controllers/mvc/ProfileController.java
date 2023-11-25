package org.pah_monitoring.main.controllers.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/{id}")
public class ProfileController {

    @RequestMapping
    public String returnProfilePage(Model model, Authentication authentication) {
        return "profile";
    }

}
