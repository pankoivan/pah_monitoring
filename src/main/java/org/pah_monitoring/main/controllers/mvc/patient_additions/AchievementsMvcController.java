package org.pah_monitoring.main.controllers.mvc.patient_additions;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/achievements")
@PreAuthorize("isAuthenticated()")
public class AchievementsMvcController {

    private final AchievementService service;

    private final AchievementService achievementService;

    private final PageHeaderService pageHeaderService;

    private final PatientService patientService;

    @GetMapping("/all")
    public String getAchievementsPage(Model model) {
        model.addAttribute("achievements", service.findAll());
        pageHeaderService.addHeader(model);
        return "patient_additions/achievements";
    }

    @GetMapping("/for/{patientId}")
    public String getPatientAchievementsPage(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            service.checkAccessRightsForObtainingPatientAchievements(patient);
            model.addAttribute("achievements", achievementService.findAllByPatientId(patient.getId()));
            pageHeaderService.addHeader(model);
            return "patient_additions/achievements";
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
