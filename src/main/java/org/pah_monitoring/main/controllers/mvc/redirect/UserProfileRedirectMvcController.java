package org.pah_monitoring.main.controllers.mvc.redirect;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.entities.main.users.users.common.User;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserProfileRedirectMvcController {

    private final UserSearchingService service;

    @GetMapping("/{id}")
    public String redirectProfilePage(@PathVariable("id") String pathId) {
        try {
            User user = service.findUserByUserInformationId(service.parsePathId(pathId));
            return switch (user) {
                case MainAdministrator ignored -> "redirect:/main-admin";
                case Administrator administrator -> "redirect:/admins/%s".formatted(administrator.getId());
                case Doctor doctor -> "redirect:/doctors/%s".formatted(doctor.getId());
                case Patient patient -> "redirect:/patients/%s".formatted(patient.getId());
                default -> throw new UrlValidationMvcControllerException(
                        "Неожиданный класс пользователя: \"%s\"".formatted(user.getClass())
                );
            };
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
    }

}
