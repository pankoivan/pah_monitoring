package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/all-admins")
public class AllAdminsController {

    @Autowired
    private AdministratorRepository administratorRepository;

    @RequestMapping
    public String returnAllAdminsPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        MainAdministrator mainAdministrator = AuthenticationUtils.extractCurrentUser(authentication, MainAdministrator.class);
        model.addAttribute("currentUser", mainAdministrator);
        model.addAttribute("nameLastname", "%s %s".formatted(
                mainAdministrator.getUserInformation().getName(),
                mainAdministrator.getUserInformation().getLastname()
        ));
        model.addAttribute("header1", "fragments/main-admin-header");
        model.addAttribute("header", "main-admin-header");

        model.addAttribute("allAdmins", administratorRepository.findAll());

        return "all-admins";

    }

}
