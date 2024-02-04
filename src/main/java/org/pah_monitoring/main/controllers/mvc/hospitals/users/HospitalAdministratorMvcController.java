package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.adding.AdministratorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.AdministratorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.filtration.enums.users.AdministratorFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.AdministratorSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals/{id}/admins")
@PreAuthorize("isAuthenticated()")
public class HospitalAdministratorMvcController {

    private final HospitalService hospitalService;

    @Qualifier("administratorService")
    private final HospitalUserService<Administrator, AdministratorAddingDto, AdministratorEditingDto, AdministratorSavingDto> service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getHospitalAdminsPage(Model model, @PathVariable("id") String pathId, @RequestParam Map<String, String> parameters) {
        try {
            Hospital hospital = hospitalService.findById(hospitalService.parsePathId(pathId));
            service.checkAccessRightsForObtainingAllInHospital(hospital);
            EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
            model.addAttribute("users", service.findAllByHospitalId(hospital.getId(), parameters, pageStat));
            model.addAttribute("currentPage", pageStat.getCurrentPage());
            model.addAttribute("pagesCount", pageStat.getPagesCount());
            model.addAttribute("filtrationProperties", AdministratorFiltrationProperty.values());
            model.addAttribute("sortingProperties", AdministratorSortingProperty.values());
            model.addAttribute("title", "Администраторы");
            model.addAttribute("usersListDescription", "Список администраторов");
            model.addAttribute("emptyUsersListMessage", "Список администраторов пуст");
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
