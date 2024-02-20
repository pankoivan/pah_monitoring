package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
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
@RequestMapping("/patients/{patientId}/examinations/tables")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorTableMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/spirometry")
    public String spirometry(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/spirometry-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/walk-test")
    public String walkTest(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/walk-test-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pulse-oximetry")
    public String pulseOximetry(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/pulse-oximetry-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/cough")
    public String cough(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/cough-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/chest-pain")
    public String chestPain(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/chest-pain-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/fainting")
    public String fainting(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/fainting-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/physical-changes")
    public String physicalChanges(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/physical-changes-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/overall-health")
    public String overallHealth(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/overall-health-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/vertigo")
    public String vertigo(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/vertigo-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pressure")
    public String pressure(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/pressure-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/liquid")
    public String liquid(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/liquid-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/weight")
    public String weight(Model model, @PathVariable("patientId") String pathPatientId) {
        try {
            model.addAttribute("patient", patientService.findById(patientService.parsePathId(pathPatientId)));
            pageHeaderService.addHeader(model);
            return "indicators/tables/weight-table";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
