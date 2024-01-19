package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.users.users.implementations.common.AbstractPatientServiceImpl;
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
@RequestMapping("/doctors")
public class DoctorMvcController {

    @Qualifier("doctorService")
    private final HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> service;

    private final AbstractPatientServiceImpl patientService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getDoctors(Model model) {
        model.addAttribute("doctors", service.findAll());
        return "users/doctors";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getDoctor(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            service.checkAccessForObtainingUser(doctor);
            model.addAttribute("doctor", doctor);
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
        return "users/profiles/doctor-profile";
    }

    @GetMapping("/{id}/patients")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DOCTOR')")
    public String getDoctorPatients(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            patientService.checkAccessForObtainingDoctorPatients(doctor);
            model.addAttribute(
                    "doctorPatients",
                    patientService.findAllByDoctorId(doctor.getId())
            );
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
        return "users/patients";
    }

}
