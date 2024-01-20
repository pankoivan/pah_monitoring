package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
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
@RequestMapping("/hospitals/{hospitalId}/doctors")
public class HospitalDoctorMvcController {

    private final HospitalService hospitalService;

    @Qualifier("doctorService")
    private final HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> service;

    @GetMapping
    public String getHospitalDoctors(Model model, @PathVariable("hospitalId") String pathHospitalId) {
        try {
            Hospital hospital = hospitalService.findById(hospitalService.parsePathId(pathHospitalId));
            service.checkAccessRightsForObtainingAllInHospital(hospital);
            model.addAttribute("doctors", service.findAllByHospitalId(hospital.getId()));
            return "users/doctors";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
