package org.pah_monitoring.main.controllers.mvc;

import jakarta.servlet.http.HttpSession;
import org.pah_monitoring.main.entities.*;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.pah_monitoring.main.repositorites.DoctorRepository;
import org.pah_monitoring.main.repositorites.PatientRepository;
import org.pah_monitoring.main.repositorites.RegistrationSecurityCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RegistrationSecurityCodeRepository codeRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping("/login")
    public String returnLoginPage() {
        return "login";
    }

    @RequestMapping("/registration")
    public String returnRegistrationPage(Model model,
                                         HttpSession session,
                                         @SessionAttribute(value = "email", required = false) String email,
                                         @SessionAttribute(value = "password", required = false) String password,
                                         @SessionAttribute(value = "name", required = false) String name,
                                         @SessionAttribute(value = "lastname", required = false) String lastname,
                                         @SessionAttribute(value = "patronymic", required = false) String patronymic,
                                         @SessionAttribute(value = "role", required = false) Role role,
                                         @SessionAttribute(value = "code", required = false) String code,
                                         @SessionAttribute(value = "error", required = false) String error) {

        model.addAttribute("email", email);
        model.addAttribute("password", password);
        model.addAttribute("name", name);
        model.addAttribute("lastname", lastname);
        model.addAttribute("patronymic", patronymic);
        model.addAttribute("role", role);
        model.addAttribute("code", code);
        model.addAttribute("error", error);

        session.removeAttribute("email");
        session.removeAttribute("password");
        session.removeAttribute("name");
        session.removeAttribute("lastname");
        session.removeAttribute("patronymic");
        session.removeAttribute("role");
        session.removeAttribute("code");
        session.removeAttribute("error");

        model.addAttribute("roles", List.of(
                Role.ADMINISTRATOR,
                Role.DOCTOR,
                Role.PATIENT
        ));

        return "registration";
    }

    @RequestMapping("/registration/processing")
    public String redirectLoginPageAfterRegistration(Model model,
                                                     HttpSession session,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("lastname") String lastname,
                                                     @RequestParam("patronymic") String patronymic,
                                                     @RequestParam("role") Role role,
                                                     @RequestParam("code") String code) {

        String error = null;
        boolean flag = false;

        UUID convertedCode = null;
        try {
            convertedCode = UUID.fromString(code);
        } catch (IllegalArgumentException e) {
            flag = true;
            error = "Код не представляет собой UUID";
        }

        if (codeRepository.findByCode(convertedCode).isEmpty()) {
            flag = true;
            error = "Такого кода не существует";
        }

        Optional<RegistrationSecurityCode> realCode = codeRepository.findByCode(convertedCode);

        if (realCode.isPresent() && !realCode.get().getRole().equals(role)) {
            flag = true;
            error = "Код не предназначен для роли \"%s\"".formatted(role);
        }

        if (flag) {
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            session.setAttribute("name", name);
            session.setAttribute("lastname", lastname);
            session.setAttribute("patronymic", patronymic);
            session.setAttribute("role", role);
            session.setAttribute("code", code);
            session.setAttribute("error", error);
            return "redirect:/auth/registration";
        }

        switch (role) {
            case ADMINISTRATOR -> {
                Administrator administrator = Administrator.builder()
                        .hospitalId(realCode.get().getHospitalId())
                        .employeeInformation(
                                EmployeeInformation.builder()
                                        .userInformation(
                                                UserInformation.builder()
                                                        .name(name)
                                                        .lastname(lastname)
                                                        .patronymic(patronymic)
                                                        .build()
                                        )
                                        .build()
                        )
                        .userSecurityInformation(
                                UserSecurityInformation.builder()
                                        .email(email)
                                        .password(new BCryptPasswordEncoder().encode(password))
                                        .build()
                        )
                        .build();
                administratorRepository.save(administrator);
            }
            case DOCTOR -> {
                Doctor doctor = Doctor.builder()
                        .hospitalId(realCode.get().getHospitalId())
                        .employeeInformation(
                                EmployeeInformation.builder()
                                        .userInformation(
                                                UserInformation.builder()
                                                        .name(name)
                                                        .lastname(lastname)
                                                        .patronymic(patronymic)
                                                        .build()
                                        )
                                        .build()
                        )
                        .userSecurityInformation(
                                UserSecurityInformation.builder()
                                        .email(email)
                                        .password(new BCryptPasswordEncoder().encode(password))
                                        .build()
                        )
                        .build();
                doctorRepository.save(doctor);
            }
            case PATIENT -> {
                Patient patient = Patient.builder()
                        .hospitalId(realCode.get().getHospitalId())
                        .userInformation(
                                UserInformation.builder()
                                        .name(name)
                                        .lastname(lastname)
                                        .patronymic(patronymic)
                                        .build()
                        )
                        .userSecurityInformation(
                                UserSecurityInformation.builder()
                                        .email(email)
                                        .password(new BCryptPasswordEncoder().encode(password))
                                        .build()
                        )
                        .build();
                patientRepository.save(patient);
            }
        }

        return "redirect:/auth/login";
    }

}
