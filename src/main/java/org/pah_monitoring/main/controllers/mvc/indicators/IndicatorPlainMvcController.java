package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.OverallHealthAddingDto;
import org.pah_monitoring.main.dto.in.examinations.indicators.PhysicalChangesAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.OverallHealth;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
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
@RequestMapping("/patients/{patientId}/examinations/")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorPlainMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Qualifier("physicalChangesService")
    private final InputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto> physicalChangesService;

    @Qualifier("overallHealthService")
    private final InputIndicatorService<OverallHealth, OverallHealthAddingDto> overallHealthService;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/physical-changes/{id}")
    public String physicalChanges(Model model,
                                  @PathVariable("patientId") String pathPatientId,
                                  @PathVariable("id") String pathId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            physicalChangesService.checkAccessRightsForObtaining(patient);
            model.addAttribute("physicalChanges", physicalChangesService.findById(physicalChangesService.parsePathId(pathId)));
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/plain/physical-changes-plain";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/overall-health/{id}")
    public String overallHealth(Model model,
                                @PathVariable("patientId") String pathPatientId,
                                @PathVariable("id") String pathId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            overallHealthService.checkAccessRightsForObtaining(patient);
            model.addAttribute("overallHealth", overallHealthService.findById(overallHealthService.parsePathId(pathId)));
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/plain/overall-health-plain";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
