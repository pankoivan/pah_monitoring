package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.main.users.users.MainAdministrator;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.main.users.users.interfaces.MainAdministratorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/main-admin")
@PreAuthorize("hasAnyRole('MAIN_ADMINISTRATOR', 'ADMINISTRATOR')")
public class MainAdministratorMvcController {

    private final MainAdministratorService service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getMainAdminPage(Model model) {
        MainAdministrator mainAdministrator = service.get();
        model.addAttribute("user", mainAdministrator);
        model.addAttribute("isSelf", checkService.isSameUser(mainAdministrator));
        model.addAttribute("isHospitalUser", false);
        model.addAttribute("isHospitalEmployee", false);
        model.addAttribute("isDoctor", false);
        model.addAttribute("isPatient", false);
        model.addAttribute("isMessageEnabled", checkService.isAdministrator());
        model.addAttribute("isNonLoginInfoEditingEnabled", checkService.isSameUser(mainAdministrator));
        pageHeaderService.addHeader(model);
        return "users/user";
    }

}
