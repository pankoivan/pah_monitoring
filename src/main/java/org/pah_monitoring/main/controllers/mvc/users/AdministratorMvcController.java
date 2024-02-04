package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.dto.in.users.users.adding.AdministratorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.AdministratorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.main.enums.Gender;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/admins")
public class AdministratorMvcController {

    @Qualifier("administratorService")
    private final HospitalUserService<Administrator, AdministratorAddingDto, AdministratorEditingDto, AdministratorSavingDto> service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getAdminsPage(Model model, @RequestParam Map<String, String> parameters) {
        EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
        model.addAttribute("users", service.findAll(parameters, pageStat));
        model.addAttribute("currentPage", pageStat.getCurrentPage());
        model.addAttribute("pagesCount", pageStat.getPagesCount());
        model.addAttribute("filtrationProperties", AdministratorFiltrationProperty.values());
        model.addAttribute("sortingProperties", AdministratorSortingProperty.values());
        model.addAttribute("title", "Администраторы");
        model.addAttribute("usersListDescription", "Список администраторов");
        model.addAttribute("emptyUsersListMessage", "Список администраторов пуст");
        pageHeaderService.addHeader(model);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getAdminPage(Model model, @PathVariable("id") String pathId) {
        try {
            Administrator administrator = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForObtainingConcrete(administrator);
            model.addAttribute("user", administrator);
            model.addAttribute("currentInactivity", administrator.getCurrentInactivity().orElse(null));
            model.addAttribute("sourcePhoneNumber", PhoneNumberUtils.toSource(administrator.getUserInformation().getPhoneNumber()));
            model.addAttribute("genders", Gender.values());
            model.addAttribute("currentDate", LocalDate.now());
            model.addAttribute("isSelf", checkService.isSameUser(administrator));
            model.addAttribute("isCurrentUserHospitalUserFromSameHospital", checkService.isHospitalUserFromSameHospital(administrator.getHospital()));
            model.addAttribute("isCurrentUserAdminFromSameHospital", checkService.isAdministratorFromSameHospital(administrator.getHospital()));
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

}
