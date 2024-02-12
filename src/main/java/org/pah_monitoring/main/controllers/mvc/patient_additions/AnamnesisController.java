package org.pah_monitoring.main.controllers.mvc.patient_additions;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.TrueFalseEnum;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class AnamnesisController {

    private final PageHeaderService pageHeaderService;

    @GetMapping("/anamnesis")
    public String getForm(Model model) {
        model.addAttribute("yesNo", TrueFalseEnum.values());
        model.addAttribute("bloodClotting", Anamnesis.BloodClotting.values());
        pageHeaderService.addHeader(model);
        return "patients/anamnesis-form";
    }

    @GetMapping("/patients/{patientId}/anamnesis")
    public String getPage() {
        return null;
    }

}
