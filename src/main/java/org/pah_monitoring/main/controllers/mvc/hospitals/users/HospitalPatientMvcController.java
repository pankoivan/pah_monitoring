package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals/{hospitalId}/patients")
public class HospitalPatientMvcController {

    private final HospitalService hospitalService;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> service;

    @GetMapping
    public String getHospitalPatients(Model model, @PathVariable("hospitalId") String pathHospitalId) {
        try {
            Hospital hospital = hospitalService.findById(hospitalService.parsePathId(pathHospitalId));
            service.checkAccessRightsForObtainingAllInHospital(hospital);
            model.addAttribute("patients", service.findAllByHospitalId(hospital.getId()));
            return "users/patients";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
