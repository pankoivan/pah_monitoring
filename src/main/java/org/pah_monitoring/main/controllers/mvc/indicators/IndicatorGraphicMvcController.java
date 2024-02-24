package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.SpirometryAddingDto;
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
    private final IndicatorService<Spirometry, SpirometryAddingDto> accessRightsCheckService;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/spirometry")
    public String getSpirometryGraphic(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/spirometry-graphic";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/walk-test")
    public String getWalkTestGraphic(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/walk-test-graphic";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pulse-oximetry")
    public String getPulseOximetryGraphic(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/pulse-oximetry-graphic";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pressure")
    public String getPressureGraphic(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/pressure-graphic";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/liquid")
    public String getLiquidGraphic(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/liquid-graphic";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/weight")
    public String getWeightGraphic(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            accessRightsCheckService.checkAccessRightsForObtaining(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/graphics/weight-graphic";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
