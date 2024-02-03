package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.dto.in.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.main.enums.Gender;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.main.users.users.implementations.common.AbstractPatientServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
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

    private final CurrentUserCheckService checkService;

    private final AbstractPatientServiceImpl patientService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getDoctorsPage(Model model) {
        model.addAttribute("users", service.findAll());
        model.addAttribute("title", "Врачи");
        model.addAttribute("usersListDescription", "Список врачей");
        model.addAttribute("emptyUsersListMessage", "Список врачей пуст");
        pageHeaderService.addHeader(model);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getDoctorPage(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(doctor);
            model.addAttribute("user", doctor);
            model.addAttribute("currentInactivity", doctor.getCurrentInactivity().orElse(null));
            model.addAttribute("sourcePhoneNumber", PhoneNumberUtils.toSource(doctor.getUserInformation().getPhoneNumber()));
            model.addAttribute("genders", Gender.values());
            model.addAttribute("isSelf", checkService.isSameUser(doctor));
            model.addAttribute("isCurrentUserHospitalUserFromSameHospital", checkService.isHospitalUserFromSameHospital(doctor.getHospital()));
            model.addAttribute("isCurrentUserAdminFromSameHospital", checkService.isAdministratorFromSameHospital(doctor.getHospital()));
            model.addAttribute("isCurrentUserMainAdministrator", checkService.isMainAdministrator());
            model.addAttribute("target", "#inactivity-selection-modal");
            pageHeaderService.addHeader(model);
            return "users/user";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/{id}/patients")
    public String getDoctorPatientsPage(Model model, @PathVariable("id") String pathId) {
        try {
            Doctor doctor = service.findById(service.parsePathId(pathId));
            patientService.checkAccessRightsForObtainingDoctorPatients(doctor);
            model.addAttribute("users", patientService.findAllByDoctorId(doctor.getId()));
            model.addAttribute("title", "Пациенты");
            model.addAttribute("usersListDescription", "Список пациентов");
            model.addAttribute("emptyUsersListMessage", "Список пациентов пуст");
            pageHeaderService.addHeader(model);
            return "users/users";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
