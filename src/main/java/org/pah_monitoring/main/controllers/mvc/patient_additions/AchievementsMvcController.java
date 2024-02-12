package org.pah_monitoring.main.controllers.mvc.patient_additions;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.TrueFalseEnum;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class AchievementsMvcController {

    private final AchievementService service;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/achievements")
    public String getAchievementsPage(Model model) {
        model.addAttribute("achievements", service.findAll());
        pageHeaderService.addHeader(model);
        return "patients/achievements";
    }

    @GetMapping("/patients/{patientId}/achievements")
    public String getPatientAchievementsPage(Model model) {
        model.addAttribute("achievements", null);
        pageHeaderService.addHeader(model);
        return "patients/achievements";
    }

}
