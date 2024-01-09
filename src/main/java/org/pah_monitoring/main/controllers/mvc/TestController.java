package org.pah_monitoring.main.controllers.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("permitAll()")
public class TestController {

    @GetMapping("/test")
    public String get() {
        return "test";
    }

    @PostMapping("/test/processing")
    public String post() {
        return "redirect:/test";
    }

}
