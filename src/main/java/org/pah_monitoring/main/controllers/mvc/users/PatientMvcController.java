package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
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
@RequestMapping("/patients")
public class PatientMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> service;

    private final PageHeaderService pageHeaderService;

    private final AccessRightsCheckService checkService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getPatients(Model model) {
        model.addAttribute("users", service.findAll());
        model.addAttribute("title", "Пациенты");
        model.addAttribute("usersListDescription", "Список пациентов");
        model.addAttribute("emptyUsersListMessage", "Список пациентов пуст");
        pageHeaderService.addHeader(model);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getPatient(Model model, @PathVariable("id") String pathId) {
        try {
            Patient patient = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(patient);
            model.addAttribute("user", patient);
            model.addAttribute("isEmployee", false);
            model.addAttribute("isPatient", true);
            model.addAttribute("isDoctor", false);
            model.addAttribute("isSelf", checkService.isSameUser(patient));
            model.addAttribute("isHospitalUser", true);
            model.addAttribute(
                    "isCurrentUserAdminFromSameHospital",
                    checkService.isAdministratorFromSameHospital(patient.getHospital())
            );
            model.addAttribute(
                    "isActivityEditingEnabled",
                    checkService.isOwnDoctor(patient)
            );
            model.addAttribute(
                    "isMessageEnabled",
                    checkService.isHospitalUserFromSameHospital(patient.getHospital()) &&
                    !checkService.isSameUser(patient)
            );
            model.addAttribute(
                    "isNonLoginInfoEditingEnabled",
                    checkService.isSameUser(patient) ||
                    checkService.isAdministratorFromSameHospital(patient.getHospital())
            );
            model.addAttribute("isCurrentUserOwnDoctor", checkService.isOwnDoctor(patient));
            pageHeaderService.addHeader(model);
            return "users/user";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
