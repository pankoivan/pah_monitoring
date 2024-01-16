package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/hospitals")
@PreAuthorize("permitAll()")
public class HospitalMvcController {

    private HospitalService service;

}
