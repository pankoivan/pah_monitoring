package org.pah_monitoring.main.controllers.mvc.patient_additions;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.enums.TrueFalseEnum;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.RedirectService;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AnamnesisService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/anamnesis")
@PreAuthorize("isAuthenticated()")
public class AnamnesisController {

    private final AnamnesisService service;

    private final RedirectService redirectService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/form")
    @PreAuthorize("hasRole('PATIENT')")
    public String getForm(Model model) {
        if (redirectService.checkPatientAnamnesisRedirect()) {
            return redirectService.patientAnamnesisRedirect();
        }
        model.addAttribute("trueFalse", TrueFalseEnum.values());
        model.addAttribute("bloodClotting", Anamnesis.BloodClotting.values());
        pageHeaderService.addHeader(model);
        return "patients/anamnesis-form";
    }

    @GetMapping("/for/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public String getPage(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("anamnesis", service.findByPatientId(service.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "patients/anamnesis";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
