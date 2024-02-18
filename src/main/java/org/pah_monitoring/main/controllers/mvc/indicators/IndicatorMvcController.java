package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.EventDuration;
import org.pah_monitoring.main.entities.main.enums.TrueFalseEnum;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/indicators/form")
@PreAuthorize("hasRole('PATIENT')")
public class IndicatorMvcController {

    @GetMapping("/spirometry")
    public String spirometry() {
        return "spirometry";
    }

    @GetMapping("/walk-test")
    public String walkTest(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("breathlessness", WalkTest.Breathlessness.values());
        return "walk-test";
    }

    @GetMapping("/pulse-oximetry")
    public String pulseOximetry(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "pulse-oximetry";
    }

    @GetMapping("/cough")
    public String cough(Model model) {
        model.addAttribute("type", Cough.Type.values());
        model.addAttribute("power", Cough.Power.values());
        model.addAttribute("timbre", Cough.Timbre.values());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "cough";
    }

    @GetMapping("/chest-pain")
    public String chestPain(Model model) {
        model.addAttribute("type", ChestPain.Type.values());
        model.addAttribute("duration", EventDuration.forChestPain());
        model.addAttribute("nitroglycerin", ChestPain.Nitroglycerin.values());
        return "chest-pain";
    }

    @GetMapping("/fainting")
    public String fainting(Model model) {
        model.addAttribute("duration", EventDuration.forFainting());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "fainting";
    }

    @GetMapping("/physical-changes")
    public String physicalChanges(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("legsSwelling", PhysicalChanges.LegsSwelling.values());
        model.addAttribute("skinColor", PhysicalChanges.SkinColor.values());
        return "physical-changes";
    }

    @GetMapping("/overall-health")
    public String overallHealth(Model model) {
        model.addAttribute("conditions", OverallHealth.Conditions.values());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("weakness", OverallHealth.Weakness.values());
        model.addAttribute("coldExtremities", OverallHealth.ColdExtremities.values());
        return "overall-health";
    }

    @GetMapping("/vertigo")
    public String vertigo(Model model) {
        model.addAttribute("duration", EventDuration.forVertigo());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "vertigo";
    }

    @GetMapping("/pressure")
    public String pressure(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "pressure";
    }

    @GetMapping("/liquid")
    public String liquid() {
        return "liquid";
    }

    @GetMapping("/weight")
    public String weight() {
        return "weight";
    }

    /*@GetMapping("/analysis-file")
    public String analysisFile(Model model) {
        return "spirometry";
    }*/

}
