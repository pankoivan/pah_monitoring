package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.Gender;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.filtration.enums.users.PatientFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.PatientSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/patients")
@PreAuthorize("isAuthenticated()")
public class PatientMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getPatientsPage(Model model, @RequestParam Map<String, String> parameters) {
        EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
        model.addAttribute("users", service.findAll(parameters, pageStat));
        model.addAttribute("currentPage", pageStat.getCurrentPage());
        model.addAttribute("pagesCount", pageStat.getPagesCount());
        model.addAttribute("filtrationProperties", PatientFiltrationProperty.subset());
        model.addAttribute("sortingProperties", PatientSortingProperty.values());
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
            model.addAttribute("currentInactivity", patient.getCurrentInactivity().orElse(null));
            model.addAttribute("isSelf", checkService.isSelf(patient));
            model.addAttribute("isCurrentUserHospitalUserFromSameHospital", checkService.isHospitalUserFromSameHospital(patient.getHospital()));
            model.addAttribute("isCurrentUserAdminFromSameHospital", checkService.isAdministratorFromSameHospital(patient.getHospital()));
            model.addAttribute("isCurrentUserDoctorFromSameHospital", checkService.isDoctorFromSameHospital(patient.getHospital()));
            model.addAttribute("isCurrentUserOwnDoctor", checkService.isOwnDoctor(patient));
            model.addAttribute("sourcePhoneNumber", PhoneNumberUtils.toSource(patient.getUserInformation().getPhoneNumber()));
            model.addAttribute("genders", Gender.values());
            model.addAttribute("currentDate", LocalDate.now());
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
