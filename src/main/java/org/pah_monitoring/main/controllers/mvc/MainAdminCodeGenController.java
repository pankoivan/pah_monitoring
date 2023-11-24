package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.*;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.repositorites.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.implementations.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/main-admin-code-gen")
public class MainAdminCodeGenController {

    @Autowired
    private RegistrationSecurityCodeRepository codeRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @RequestMapping
    public String returnCodeGenPage(Model model, Authentication authentication) {

        if (AuthenticationUtils.checkConcreteUserClass(authentication, MainAdministrator.class)) {
            model.addAttribute("header1", "fragments/main-admin-header");
            model.addAttribute("header", "main-admin-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Administrator.class)) {
            model.addAttribute("header1", "fragments/admin-header");
            model.addAttribute("header", "admin-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Doctor.class)) {
            model.addAttribute("header1", "fragments/doctor-header");
            model.addAttribute("header", "doctor-header");
        } else if (AuthenticationUtils.checkConcreteUserClass(authentication, Patient.class)) {
            model.addAttribute("header1", "fragments/patient-header");
            model.addAttribute("header", "patient-header");
        }

        model.addAttribute("roles", List.of(Role.ADMINISTRATOR));
        model.addAttribute("expire", List.of(1, 3, 7, 30));

        return "main-admin-code-gen";
    }

    @RequestMapping("/processing")
    public String redirectCodeGenPageAfterCodeGeneration(@RequestParam("oid") String oid,
                                                         @RequestParam("expire") int expire,
                                                         @RequestParam("role") Role role,
                                                         @RequestParam("email") String email) {

        UUID uuid = UUID.randomUUID();

        RegistrationSecurityCode genCode = RegistrationSecurityCode.builder()
                .code(uuid)
                .hospitalId(oid)
                .expirationDate(LocalDate.now().plusDays(expire))
                .role(role)
                .build();

        codeRepository.save(genCode);

        System.out.println(uuid);

        emailService.send(email, "Код регистрации", uuid.toString());

        return "redirect:/main-admin-code-gen";
    }

}
