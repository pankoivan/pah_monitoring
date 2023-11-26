package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.Administrator;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.pah_monitoring.main.entities.RegistrationSecurityCode;
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
@RequestMapping("/admin-code-gen")
public class AdminCodeGenController {

    @Autowired
    private RegistrationSecurityCodeRepository codeRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @RequestMapping
    public String returnCodeGenPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        Administrator administrator = AuthenticationUtils.extractCurrentUser(authentication, Administrator.class);
        model.addAttribute("currentUser", administrator);
        model.addAttribute("nameLastname", "%s %s".formatted(
                administrator.getEmployeeInformation().getUserInformation().getName(),
                administrator.getEmployeeInformation().getUserInformation().getLastname()
        ));
        model.addAttribute("header1", "fragments/admin-header");
        model.addAttribute("header", "admin-header");

        model.addAttribute("roles", List.of(Role.ADMINISTRATOR, Role.DOCTOR, Role.PATIENT));
        model.addAttribute("expire", List.of(1, 3, 7, 30));

        return "admin-code-gen";
    }

    @RequestMapping("/processing")
    public String redirectCodeGenPageAfterCodeGeneration(Authentication authentication,
                                                         @RequestParam("expire") int expire,
                                                         @RequestParam("role") Role role,
                                                         @RequestParam("email") String email) throws AuthenticationUtilsException {

        UUID uuid = UUID.randomUUID();

        RegistrationSecurityCode genCode = RegistrationSecurityCode.builder()
                .code(uuid)
                .hospitalId(AuthenticationUtils.extractCurrentUser(authentication, Administrator.class).getHospitalId())
                .expirationDate(LocalDate.now().plusDays(expire))
                .role(role)
                .build();

        codeRepository.save(genCode);

        System.out.println(uuid);

        emailService.send(email, "Код регистрации", uuid.toString());

        return "redirect:/admin-code-gen";
    }

}
