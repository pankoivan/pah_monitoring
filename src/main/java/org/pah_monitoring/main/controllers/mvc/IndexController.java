package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.Administrator;
import org.pah_monitoring.main.entities.Doctor;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.pah_monitoring.main.entities.Patient;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String returnIndexPage(Model model, Authentication authentication) {

        if (AuthenticationUtils.checkConcreteUserClass(authentication, MainAdministrator.class)) {
            model.addAttribute("header1", "fragments/main-admin-header");
            model.addAttribute("header", "main-admin-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Administrator.class)) {
            //System.out.println("-------------------------- THAT IS ADMIN ------------------------------------------");
            model.addAttribute("header1", "fragments/admin-header");
            model.addAttribute("header", "admin-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Doctor.class)) {
            model.addAttribute("header1", "fragments/doctor-header");
            model.addAttribute("header", "doctor-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Patient.class)) {
            model.addAttribute("header1", "fragments/patient-header");
            model.addAttribute("header", "patient-header");
        }

        if (AuthenticationUtils.checkConcreteUserClass(authentication, MainAdministrator.class)) {
            return "redirect:/main-admin-code-gen";
        }

        return "index";
    }

}
