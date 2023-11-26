package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.Administrator;
import org.pah_monitoring.main.entities.Doctor;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
public class AdminsController {

    @Autowired
    private AdministratorRepository administratorRepository;

    @RequestMapping
    public String returnAdminsPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        Administrator administrator = null;
        Doctor doctor = null;

        if (AuthenticationUtils.checkConcreteUserClass(authentication, Administrator.class)) {

            administrator = AuthenticationUtils.extractCurrentUser(authentication, Administrator.class);

            model.addAttribute("currentUser", administrator);
            model.addAttribute("nameLastname", "%s %s".formatted(
                    administrator.getEmployeeInformation().getUserInformation().getName(),
                    administrator.getEmployeeInformation().getUserInformation().getLastname()
            ));
            model.addAttribute("header1", "fragments/admin-header");
            model.addAttribute("header", "admin-header");

            model.addAttribute("admins", administratorRepository.findAllByHospitalId(administrator.getHospitalId()));

        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Doctor.class)) {

            doctor = AuthenticationUtils.extractCurrentUser(authentication, Doctor.class);

            model.addAttribute("currentUser", doctor);
            model.addAttribute("nameLastname", "%s %s".formatted(
                    doctor.getEmployeeInformation().getUserInformation().getName(),
                    doctor.getEmployeeInformation().getUserInformation().getLastname()
            ));
            model.addAttribute("header1", "fragments/doctor-header");
            model.addAttribute("header", "doctor-header");

            model.addAttribute("admins", administratorRepository.findAllByHospitalId(doctor.getHospitalId()));
        }

        return "admins";

    }

}
