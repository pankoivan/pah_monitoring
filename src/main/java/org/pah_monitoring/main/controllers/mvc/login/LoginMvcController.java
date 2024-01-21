package org.pah_monitoring.main.controllers.mvc.login;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginMvcController {

    @GetMapping
    public String getLogin(Authentication authentication) {
        if (authentication instanceof User) {
            return "redirect:/";
        }
        return "login";
    }

}
