package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
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
@RequestMapping("/patients")
public class PatientMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getPatientsPage(Model model) {
        model.addAttribute("users", service.findAll());
        model.addAttribute("title", "Пациенты");
        model.addAttribute("usersListDescription", "Список пациентов");
        model.addAttribute("emptyUsersListMessage", "Список пациентов пуст");
        pageHeaderService.addHeader(model);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getPatientPage(Model model, @PathVariable("id") String pathId) {
        try {
            Patient patient = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(patient);
            model.addAttribute("user", patient);
            model.addAttribute("isSelf", checkService.isSameUser(patient));
            model.addAttribute("isCurrentUserHospitalUserFromSameHospital", checkService.isHospitalUserFromSameHospital(patient.getHospital()));
            model.addAttribute("isCurrentUserAdminFromSameHospital", checkService.isAdministratorFromSameHospital(patient.getHospital()));
            model.addAttribute("isCurrentUserOwnDoctor", checkService.isOwnDoctor(patient));
            model.addAttribute("target", "#patient-inactivity-modal");
            pageHeaderService.addHeader(model);
            return "users/user";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
