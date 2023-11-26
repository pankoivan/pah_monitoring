package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.Administrator;
import org.pah_monitoring.main.entities.Doctor;
import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.pah_monitoring.main.repositorites.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.Doc;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {

    @Autowired
    private DoctorRepository doctorRepository;

    @RequestMapping
    public String returnDoctorsPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        Administrator administrator = AuthenticationUtils.extractCurrentUser(authentication, Administrator.class);

        model.addAttribute("currentUser", administrator);
        model.addAttribute("nameLastname", "%s %s".formatted(
                administrator.getEmployeeInformation().getUserInformation().getName(),
                administrator.getEmployeeInformation().getUserInformation().getLastname()
        ));
        model.addAttribute("header1", "fragments/admin-header");
        model.addAttribute("header", "admin-header");

        model.addAttribute("doctors", doctorRepository.findAllByHospitalId(administrator.getHospitalId()));

        return "doctors";

    }

}
