package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.users.users.implementations.common.AbstractPatientServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/doctors")
public class DoctorMvcController {

    @Qualifier("doctorService")
    private final HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> service;

    private final AbstractPatientServiceImpl patientService;

    @GetMapping
    public String getDoctors(Model model) {
        try {
            service.checkAccessRightsForObtainingAll();
            model.addAttribute("doctors", service.findAll());
            return "users/doctors";
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public String getDoctor(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(doctor);
            model.addAttribute("doctor", doctor);
            return "users/profiles/doctor-profile";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/{id}/patients")
    public String getDoctorPatients(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            patientService.checkAccessRightsForObtainingDoctorPatients(doctor);
            model.addAttribute("patients", patientService.findAllByDoctorId(doctor.getId()));
            return "users/patients";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
