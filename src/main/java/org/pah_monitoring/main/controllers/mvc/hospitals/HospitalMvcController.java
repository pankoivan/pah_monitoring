package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
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

    @GetMapping
    public String getHospitals(Model model) {
        try {
            service.checkAccessRightsForObtainingAll();
            model.addAttribute("hospitals", service.findAll());
            return "hospitals/hospitals";
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public String getHospital(Model model, @PathVariable("id") String pathId) {
        try {
            Hospital hospital = service.findById(service.parsePathId(pathId));
            service.checkHospitalCurrentState(hospital);
            service.checkAccessRightsForObtainingHospital(hospital);
            model.addAttribute("hospital", hospital);
            return "hospitals/hospital";
        } catch (UrlValidationServiceException | DataSearchingServiceException | DataValidationServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
