package org.pah_monitoring.main.controllers.mvc.users;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admins")
@PreAuthorize("permitAll()")  // todo: only for main admin
public class AdministratorMvcController {

    private final AdministratorService service;

    @GetMapping
    public String getAdmins(Model model) {
        model.addAttribute("admins", service.findAll());
        return "users/admins";
    }

    @GetMapping("/{id}")
    public String getAdmin(Model model, @PathVariable("id") String pathId) {
        try {
            model.addAttribute("admin", service.findById(service.parsePathId(pathId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        }
        return "users/profiles/admin-profile";
    }

}
