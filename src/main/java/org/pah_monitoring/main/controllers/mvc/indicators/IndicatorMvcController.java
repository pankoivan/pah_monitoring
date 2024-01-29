package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndicatorMvcController {

    private final IndicatorCardService service;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/indicators")
    public String getIndicatorsPage(Model model) {
        model.addAttribute("indicators", service.getAll());
        model.addAttribute("isCurrentUserOwnDoctor", true);
        model.addAttribute("isCurrentUserPatient", false);
        pageHeaderService.addHeader(model);
        return "patients/indicators";
    }

}
