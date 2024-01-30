package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndicatorMvcController {

    private final IndicatorCardService service;

    private final CurrentUserExtractionService extractionService;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/indicators")
    @PreAuthorize("hasRole('PATIENT')")
    public String getIndicatorsPageForPatient(Model model) {
        Patient patient = extractionService.patient();
        model.addAttribute("inputIndicatorCards", service.getAllInputIndicatorCardsFor(patient));
        model.addAttribute("fileIndicatorCards", service.getAllFileIndicatorCardsFor(patient));
        model.addAttribute("isCurrentUserOwnDoctor", true);
        model.addAttribute("isCurrentUserPatient", false);
        pageHeaderService.addHeader(model);
        return "patients/indicators";
    }

    @GetMapping("/patient/{id}/indicators")
    @PreAuthorize("hasRole('DOCTOR')")
    public String getIndicatorsPageForDoctor(Model model, @PathVariable("id") String pathId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathId));
            service.checkAccessRightsForObtainingByDoctor(patient);
            model.addAttribute("inputIndicatorCards", service.getAllInputIndicatorCardsFor(patient));
            model.addAttribute("fileIndicatorCards", service.getAllFileIndicatorCardsFor(patient));
            model.addAttribute("isCurrentUserOwnDoctor", true);
            model.addAttribute("isCurrentUserPatient", false);
            pageHeaderService.addHeader(model);
            return "patients/indicators";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
