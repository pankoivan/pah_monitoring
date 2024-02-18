package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/indicators")
@PreAuthorize("hasRole('PATIENT')")
public class IndicatorMvcController {

    @GetMapping("/spirometry")
    public String spirometry(Model model) {
        return "spirometry";
    }

    @GetMapping("/walk-test")
    public String walkTest(Model model) {
        return "walk-test";
    }

    @GetMapping("/pulse-oximetry")
    public String pulseOximetry(Model model) {
        return "pulse-oximetry";
    }

    @GetMapping("/cough")
    public String cough(Model model) {
        return "cough";
    }

    @GetMapping("/chest-pain")
    public String chestPain(Model model) {
        return "chest-pain";
    }

    @GetMapping("/fainting")
    public String fainting(Model model) {
        return "fainting";
    }

    @GetMapping("/physical-changes")
    public String physicalChanges(Model model) {
        return "physical-changes";
    }

    @GetMapping("/overall-health")
    public String overallHealth(Model model) {
        return "overall-health";
    }

    @GetMapping("/vertigo")
    public String vertigo(Model model) {
        return "vertigo";
    }

    @GetMapping("/pressure")
    public String pressure(Model model) {
        return "pressure";
    }

    @GetMapping("/liquid")
    public String liquid(Model model) {
        return "liquid";
    }

    @GetMapping("/weight")
    public String weight(Model model) {
        return "weight";
    }

    /*@GetMapping("/analysis-file")
    public String analysisFile(Model model) {
        return "spirometry";
    }*/

}
