package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.*;
import org.pah_monitoring.main.repositorites.MainPageContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private MainPageContactRepository mainPageContactRepository;

    @RequestMapping
    public String returnIndexPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        if (AuthenticationUtils.checkConcreteUserClass(authentication, MainAdministrator.class)) {
            MainAdministrator mainAdministrator = AuthenticationUtils.extractCurrentUser(authentication, MainAdministrator.class);
            model.addAttribute("currentUser", mainAdministrator);
            model.addAttribute("nameLastname", "%s %s".formatted(
                    mainAdministrator.getUserInformation().getName(),
                    mainAdministrator.getUserInformation().getLastname()
            ));
            model.addAttribute("header1", "fragments/main-admin-header");
            model.addAttribute("header", "main-admin-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Administrator.class)) {
            Administrator administrator = AuthenticationUtils.extractCurrentUser(authentication, Administrator.class);
            model.addAttribute("currentUser", administrator);
            model.addAttribute("nameLastname", "%s %s".formatted(
                    administrator.getEmployeeInformation().getUserInformation().getName(),
                    administrator.getEmployeeInformation().getUserInformation().getLastname()
            ));
            model.addAttribute("header1", "fragments/admin-header");
            model.addAttribute("header", "admin-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Doctor.class)) {
            Doctor doctor = AuthenticationUtils.extractCurrentUser(authentication, Doctor.class);
            model.addAttribute("currentUser", doctor);
            model.addAttribute("nameLastname", "%s %s".formatted(
                    doctor.getEmployeeInformation().getUserInformation().getName(),
                    doctor.getEmployeeInformation().getUserInformation().getLastname()
            ));
            model.addAttribute("header1", "fragments/doctor-header");
            model.addAttribute("header", "doctor-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Patient.class)) {
            Patient patient = AuthenticationUtils.extractCurrentUser(authentication, Patient.class);
            model.addAttribute("currentUser", patient);
            model.addAttribute("nameLastname", "%s %s".formatted(
                    patient.getUserInformation().getName(),
                    patient.getUserInformation().getLastname()
            ));
            model.addAttribute("header1", "fragments/patient-header");
            model.addAttribute("header", "patient-header");
        }

        if (AuthenticationUtils.checkConcreteUserClass(authentication, MainAdministrator.class)) {
            return "redirect:/main-admin-code-gen";
        }

        model.addAttribute("formattedContacts", mainPageContactRepository.findAll()
                .stream()
                .map(a -> "%s: %s".formatted(a.getDescription(), a.getContact())));

        return "index";
    }

}
