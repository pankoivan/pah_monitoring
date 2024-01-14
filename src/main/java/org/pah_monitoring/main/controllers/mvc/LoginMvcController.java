package org.pah_monitoring.main.controllers.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginMvcController {

    @GetMapping
    public String getPage() {
        // todo: add check if user already authenticated
        return "login";
    }

}
