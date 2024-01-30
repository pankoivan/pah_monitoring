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
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/patients/{id}/indicators")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorMvcController {

    private final IndicatorCardService service;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    private final AccessRightsCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getIndicatorsPage(Model model, @PathVariable("id") String pathId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathId));
            service.checkAccessRightsForObtainingAll(patient);
            model.addAttribute("inputIndicatorCards", service.getAllInputIndicatorCardsFor(patient));
            model.addAttribute("fileIndicatorCards", service.getAllFileIndicatorCardsFor(patient));
            model.addAttribute("isCurrentUserOwnDoctor", checkService.isOwnDoctor(patient));
            model.addAttribute("isCurrentUserPatient", checkService.isSamePatient(patient));
            pageHeaderService.addHeader(model);
            return "patients/indicators";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
