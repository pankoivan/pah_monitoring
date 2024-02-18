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
        return "indicators/forms/spirometry-form";
    }

    @GetMapping("/walk-test")
    public String walkTest(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("breathlessness", WalkTest.Breathlessness.values());
        return "indicators/forms/walk-test-form";
    }

    @GetMapping("/pulse-oximetry")
    public String pulseOximetry(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "indicators/forms/pulse-oximetry-form";
    }

    @GetMapping("/cough")
    public String cough(Model model) {
        model.addAttribute("type", Cough.Type.values());
        model.addAttribute("power", Cough.Power.values());
        model.addAttribute("timbre", Cough.Timbre.values());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "indicators/forms/cough-form";
    }

    @GetMapping("/chest-pain")
    public String chestPain(Model model) {
        model.addAttribute("type", ChestPain.Type.values());
        model.addAttribute("duration", EventDuration.forChestPain());
        model.addAttribute("nitroglycerin", ChestPain.Nitroglycerin.values());
        return "indicators/forms/chest-pain-form";
    }

    @GetMapping("/fainting")
    public String fainting(Model model) {
        model.addAttribute("duration", EventDuration.forFainting());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "indicators/forms/fainting-form";
    }

    @GetMapping("/physical-changes")
    public String physicalChanges(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("legsSwelling", PhysicalChanges.LegsSwelling.values());
        model.addAttribute("skinColor", PhysicalChanges.SkinColor.values());
        return "indicators/forms/physical-changes-form";
    }

    @GetMapping("/overall-health")
    public String overallHealth(Model model) {
        model.addAttribute("conditions", OverallHealth.Conditions.values());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("weakness", OverallHealth.Weakness.values());
        model.addAttribute("coldExtremities", OverallHealth.ColdExtremities.values());
        return "indicators/forms/overall-health-form";
    }

    @GetMapping("/vertigo")
    public String vertigo(Model model) {
        model.addAttribute("duration", EventDuration.forVertigo());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "indicators/forms/vertigo-form";
    }

    @GetMapping("/pressure")
    public String pressure(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        return "indicators/pressure-form";
    }

    @GetMapping("/liquid")
    public String liquid() {
        return "indicators/forms/liquid-form";
    }

    @GetMapping("/weight")
    public String weight() {
        return "indicators/forms/weight-form";
    }

    /*@GetMapping("/analysis-file")
    public String analysisFile(Model model) {
        return "spirometry";
    }*/

}
