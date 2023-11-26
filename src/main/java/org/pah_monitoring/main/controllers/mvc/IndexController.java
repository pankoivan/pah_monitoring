package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.auxiliary.utils.TempCheck;
import org.pah_monitoring.main.entities.*;
import org.pah_monitoring.main.repositorites.MainPageContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.Doc;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private MainPageContactRepository mainPageContactRepository;

    @RequestMapping
    public String returnIndexPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        if (AuthenticationUtils.checkConcreteUserClass(authentication, MainAdministrator.class)) {
            return "redirect:/main-admin-code-gen";
        }

        if (AuthenticationUtils.checkConcreteUserClass(authentication, Administrator.class)) {
            return "redirect:/admin-code-gen";
        }

        if (AuthenticationUtils.checkConcreteUserClass(authentication, Doctor.class)) {
            return "redirect:/own-patients";
        }

        if (AuthenticationUtils.checkConcreteUserClass(authentication, Patient.class)) {
            Patient patient = AuthenticationUtils.extractCurrentUser(authentication, Patient.class);
            return "redirect:/progress/%s".formatted(patient.getId());
        }

        model.addAttribute("formattedContacts", mainPageContactRepository.findAll()
                .stream()
                .map(a -> "%s: %s".formatted(a.getDescription(), a.getContact())));

        return "index";
    }

}
