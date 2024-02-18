package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.EventDuration;
import org.pah_monitoring.main.entities.main.enums.TrueFalseEnum;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/indicators/form")
//@PreAuthorize("hasRole('PATIENT')")
@PreAuthorize("permitAll()")
public class IndicatorMvcController {

    private final PageHeaderService pageHeaderService;

    @GetMapping("/spirometry")
    public String spirometry(Model model) {
        pageHeaderService.addHeader(model);
        return "indicators/forms/spirometry-form";
    }

    @GetMapping("/walk-test")
    public String walkTest(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("breathlessness", WalkTest.Breathlessness.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/walk-test-form";
    }

    @GetMapping("/pulse-oximetry")
    public String pulseOximetry(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/pulse-oximetry-form";
    }

    @GetMapping("/cough")
    public String cough(Model model) {
        model.addAttribute("type", Cough.Type.values());
        model.addAttribute("power", Cough.Power.values());
        model.addAttribute("timbre", Cough.Timbre.values());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/cough-form";
    }

    @GetMapping("/chest-pain")
    public String chestPain(Model model) {
        model.addAttribute("type", ChestPain.Type.values());
        model.addAttribute("duration", EventDuration.forChestPain());
        model.addAttribute("nitroglycerin", ChestPain.Nitroglycerin.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/chest-pain-form";
    }

    @GetMapping("/fainting")
    public String fainting(Model model) {
        model.addAttribute("duration", EventDuration.forFainting());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/fainting-form";
    }

    @GetMapping("/physical-changes")
    public String physicalChanges(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("legsSwelling", PhysicalChanges.LegsSwelling.values());
        model.addAttribute("skinColor", PhysicalChanges.SkinColor.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/physical-changes-form";
    }

    @GetMapping("/overall-health")
    public String overallHealth(Model model) {
        model.addAttribute("conditions", OverallHealth.Conditions.values());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("weakness", OverallHealth.Weakness.values());
        model.addAttribute("coldExtremities", OverallHealth.ColdExtremities.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/overall-health-form";
    }

    @GetMapping("/vertigo")
    public String vertigo(Model model) {
        model.addAttribute("duration", EventDuration.forVertigo());
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/vertigo-form";
    }

    @GetMapping("/pressure")
    public String pressure(Model model) {
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        pageHeaderService.addHeader(model);
        return "indicators/forms/pressure-form";
    }

    @GetMapping("/liquid")
    public String liquid(Model model) {
        pageHeaderService.addHeader(model);
        return "indicators/forms/liquid-form";
    }

    @GetMapping("/weight")
    public String weight(Model model) {
        pageHeaderService.addHeader(model);
        return "indicators/forms/weight-form";
    }

    @GetMapping("/temp")
    public String temp(Model model) {
        pageHeaderService.addHeader(model);
        return "indicators/tables/pressure-table";
    }

    /*@GetMapping("/analysis-file")
    public String analysisFile(Model model) {
        return "spirometry";
    }*/

}
