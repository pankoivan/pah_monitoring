package org.pah_monitoring.main.controllers.mvc.login;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.RedirectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginMvcController {

    private final RedirectService redirectService;

    @GetMapping
    public String getLogin() {
        if (redirectService.checkNotAnonymousUserRedirect()) {
            return redirectService.notAnonymousUserRedirect();
        }
        return "login";
    }

}
