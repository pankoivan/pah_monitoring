package org.pah_monitoring.main.controllers.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital-registration-requests")
@PreAuthorize("permitAll()")
public class HospitalRegistrationRequestMvcController {

    @GetMapping
    public String getPage() {
        return "hospitals/hospital-registration";
    }

}
