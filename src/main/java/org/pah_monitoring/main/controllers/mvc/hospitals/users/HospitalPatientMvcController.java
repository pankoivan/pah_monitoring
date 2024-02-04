package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
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
@RequestMapping("/hospitals/{id}/patients")
@PreAuthorize("isAuthenticated()")
public class HospitalPatientMvcController {

    private final HospitalService hospitalService;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getHospitalPatientsPage(Model model, @PathVariable("id") String pathId) {
        try {
            Hospital hospital = hospitalService.findById(hospitalService.parsePathId(pathId));
            service.checkAccessRightsForObtainingAllInHospital(hospital);
            model.addAttribute("users", service.findAllByHospitalId(hospital.getId()));
            model.addAttribute("title", "Пациенты");
            model.addAttribute("usersListDescription", "Список пациентов");
            model.addAttribute("emptyUsersListMessage", "Список пациентов пуст");
            model.addAttribute("isCurrentUserAdministrator", checkService.isAdministrator());
            pageHeaderService.addHeader(model);
            return "users/users";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
