package org.pah_monitoring.main.controllers.mvc.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.users.users.interfaces.MainAdministratorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/main-admin")
@PreAuthorize("hasAnyRole('MAIN_ADMINISTRATOR', 'ADMINISTRATOR')")
public class MainAdministratorController {

    private final MainAdministratorService service;

    private final PageHeaderService pageHeaderService;

    private final AccessRightsCheckService checkService;

    @GetMapping
    public String getPage(Model model) {
        MainAdministrator mainAdministrator = service.get();
        model.addAttribute("user", mainAdministrator);
        model.addAttribute("isSelf", checkService.isSameUser(mainAdministrator));
        model.addAttribute("isHospitalUser", false);
        model.addAttribute("isEmployee", false);
        model.addAttribute("isDoctor", false);
        model.addAttribute("isPatient", false);
        model.addAttribute("isMessageEnabled", checkService.isAdministrator());
        model.addAttribute("isNonLoginInfoEditingEnabled", checkService.isSameUser(mainAdministrator));
        pageHeaderService.addHeader(model);
        return "users/user";
    }

}
