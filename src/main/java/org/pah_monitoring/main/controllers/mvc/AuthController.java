package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.main.entities.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/login")
    public String returnLoginPage() {
        return "login";
    }

    @RequestMapping("/registration")
    public String returnRegistrationPage(Model model) {
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @RequestMapping("/registration/processing")
    public String redirectLoginPageAfterRegistration(@RequestParam("email") String email,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("lastname") String lastname,
                                                     @RequestParam("patronymic") String patronymic,
                                                     @RequestParam("role") Role role,
                                                     @RequestParam("code") String code) {



        return "redirect:/auth/login";
    }

}
