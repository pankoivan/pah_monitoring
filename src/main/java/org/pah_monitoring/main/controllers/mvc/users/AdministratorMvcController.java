package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.AdministratorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.AdministratorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
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
@RequestMapping("/admins")
public class AdministratorMvcController {

    @Qualifier("administratorService")
    private final HospitalUserService<Administrator, AdministratorAddingDto, AdministratorEditingDto, AdministratorSavingDto> service;

    private final AccessRightsCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getAdminsPage(Model model) {
        model.addAttribute("users", service.findAll());
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
            model.addAttribute("isActive", administrator.isActive());
            model.addAttribute("activityMessage", administrator.activityMessage());
            model.addAttribute("isSelf", checkService.isSameUser(administrator));
            model.addAttribute("isHospitalUser", true);
            model.addAttribute("isHospitalEmployee", true);
            model.addAttribute("isDoctor", false);
            model.addAttribute("isPatient", false);
            model.addAttribute(
                    "isMessageEnabled",
                    checkService.isHospitalUserFromSameHospital(administrator.getHospital()) &&
                    !checkService.isSameUser(administrator)
            );
            model.addAttribute(
                    "isNonLoginInfoEditingEnabled",
                    checkService.isSameUser(administrator) ||
                    checkService.isAdministratorFromSameHospital(administrator.getHospital())
            );
            model.addAttribute( // todo: check if on vacation, sick leave or dismissed
                    "isActivityEditingEnabled",
                    checkService.isAdministratorFromSameHospital(administrator.getHospital()) &&
                    !checkService.isSameUser(administrator)
            );
            pageHeaderService.addHeader(model);
            return "users/user";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
