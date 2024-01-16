package org.pah_monitoring.main.controllers.mvc.login;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginMvcController {

    @GetMapping
    public String getLogin(@AuthenticationPrincipal Object principal) {
        return "login"; // todo: add redirect if anonymous
    }

}
