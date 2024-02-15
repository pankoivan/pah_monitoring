package org.pah_monitoring.main.controllers.mvc.login;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.RedirectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginMvcController {

    private final RedirectService redirectService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getLoginPage(Model model) {
        if (redirectService.checkNotAnonymousUserRedirect()) {
            return redirectService.notAnonymousUserRedirect();
        }
        pageHeaderService.addHeader(model);
        return "login";
    }

}
