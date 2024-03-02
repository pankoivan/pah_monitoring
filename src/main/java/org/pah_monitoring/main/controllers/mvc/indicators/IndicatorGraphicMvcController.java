package org.pah_monitoring.main.controllers.mvc.indicators;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Spirometry;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.IndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/patients/{patientId}/examinations/graphics")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorGraphicMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Qualifier("spirometryService")
    private final IndicatorService<Spirometry> accessRightsCheckService;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping({
            "/spirometry", "/walk-test", "/pulse-oximetry", "/pressure", "/liquid", "/weight",
    })
    public String getIndicatorGraphicPage(HttpServletRequest request, Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("periods", IndicatorService.Period.values());
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/%s-graphic".formatted(whichIndicator(request));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }


    private String whichIndicator(HttpServletRequest request) {
        String[] parts = request.getRequestURI().split("/");
        return parts[parts.length - 1];
    }

}
