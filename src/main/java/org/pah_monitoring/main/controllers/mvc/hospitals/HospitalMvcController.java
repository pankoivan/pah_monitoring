package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals")
public class HospitalMvcController {

    private final HospitalService service;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getHospitals(Model model) {
        model.addAttribute("hospitals", service.findAll());
        pageHeaderService.addHeader(model);
        return "hospitals/hospitals";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getHospital(Model model, @PathVariable("id") String pathId) {
        try {
            Hospital hospital = service.findById(service.parsePathId(pathId));
            service.checkHospitalCurrentState(hospital);
            service.checkAccessRightsForObtainingConcrete(hospital);
            model.addAttribute("adminStat", service.getAdministratorStatistics(hospital));
            model.addAttribute("doctorStat", service.getDoctorStatistics(hospital));
            model.addAttribute("patientStat", service.getPatientStatistics(hospital));
            pageHeaderService.addHeader(model);
            model.addAttribute("hospital", hospital);
            return "hospitals/hospital";
        } catch (UrlValidationServiceException | DataSearchingServiceException | DataValidationServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
