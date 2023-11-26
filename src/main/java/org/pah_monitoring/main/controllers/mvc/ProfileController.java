package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.auxiliary.utils.TempCheck;
import org.pah_monitoring.main.entities.Administrator;
import org.pah_monitoring.main.entities.Doctor;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.pah_monitoring.main.entities.Patient;
import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.pah_monitoring.main.repositorites.DoctorRepository;
import org.pah_monitoring.main.repositorites.MainAdministratorRepository;
import org.pah_monitoring.main.repositorites.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private MainAdministratorRepository mainAdministratorRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping("/main-admin/{id}")
    public String returnMainAdminProfilePage(Model model,
                                             Authentication authentication,
                                             @PathVariable("id") int id) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        MainAdministrator mainAdministrator = mainAdministratorRepository.findById(id).get();

        model.addAttribute("mainAdmin", mainAdministrator);

        return "main-admin-profile";
    }

    @RequestMapping("/admin/{id}")
    public String returnAdminProfilePage(Model model,
                                         Authentication authentication,
                                         @PathVariable("id") int id) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        Administrator admin = administratorRepository.findById(id).get();

        model.addAttribute("admin", admin);

        return "admin-profile";
    }

    @RequestMapping("/doctor/{id}")
    public String returnDoctorProfilePage(Model model,
                                          Authentication authentication,
                                          @PathVariable("id") int id) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        Doctor doctor = doctorRepository.findById(id).get();

        model.addAttribute("doctor", doctor);

        return "doctor-profile";
    }

    @RequestMapping("/patient/{id}")
    public String returnPatientProfilePage(Model model,
                                           Authentication authentication,
                                           @PathVariable("id") int id) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        Patient patient = patientRepository.findById(id).get();

        model.addAttribute("patient", patient);

        return "patient-profile";
    }

}
