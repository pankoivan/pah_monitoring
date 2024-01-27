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
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
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

    private final PageHeaderService pageHeaderService;

    private final AccessRightsCheckService checkService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getDoctors(Model model) {
        model.addAttribute("users", service.findAll());
        model.addAttribute("title", "Врачи");
        model.addAttribute("usersListDescription", "Список врачей");
        model.addAttribute("emptyUsersListMessage", "Список врачей пуст");
        pageHeaderService.addHeader(model);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getDoctor(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(doctor);
            model.addAttribute("user", doctor);
            model.addAttribute("isEmployee", true);
            model.addAttribute("isPatient", false);
            model.addAttribute("isSelf", checkService.isSameUser(doctor));
            model.addAttribute("isHospitalUser", true);
            model.addAttribute(
                    "isActivityEditingEnabled",
                    checkService.isAdministratorFromSameHospital(doctor.getHospital()) &&
                    !checkService.isSameUser(doctor)
            );
            model.addAttribute(
                    "isMessageEnabled",
                    checkService.isHospitalUserFromSameHospital(doctor.getHospital()) &&
                    !checkService.isSameUser(doctor)
            );
            pageHeaderService.addHeader(model);
            return "users/user";
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
            pageHeaderService.addHeader(model);
            return "users/patients";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
