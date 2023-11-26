package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.Administrator;
import org.pah_monitoring.main.entities.Doctor;
import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.pah_monitoring.main.repositorites.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/own-patients")
public class OwnPatientsController {

    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping
    public String returnOwnPatientsPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        Doctor doctor = AuthenticationUtils.extractCurrentUser(authentication, Doctor.class);

        model.addAttribute("currentUser", doctor);
        model.addAttribute("nameLastname", "%s %s".formatted(
                doctor.getEmployeeInformation().getUserInformation().getName(),
                doctor.getEmployeeInformation().getUserInformation().getLastname()
        ));
        model.addAttribute("header1", "fragments/doctor-header");
        model.addAttribute("header", "doctor-header");

        model.addAttribute("patients", patientRepository.findAllByHospitalId(doctor.getHospitalId()));

        return "own-patients";

    }

}
