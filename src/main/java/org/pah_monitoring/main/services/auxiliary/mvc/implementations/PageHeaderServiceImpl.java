package org.pah_monitoring.main.services.auxiliary.mvc.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.PageHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class PageHeaderServiceImpl implements PageHeaderService {

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

    @Override
    public void addHeader(Model model) {
        if (checkService.isMainAdministrator()) {
            model.addAttribute("headerFolder", "fragments/main-admin-header");
            model.addAttribute("headerName", "main-admin-header");
        } else if (checkService.isAdministrator()) {
            model.addAttribute("headerFolder", "fragments/admin-header");
            model.addAttribute("headerName", "admin-header");
        } else if (checkService.isDoctor()) {
            model.addAttribute("headerFolder", "fragments/doctor-header");
            model.addAttribute("headerName", "doctor-header");
        } else if (checkService.isPatient()) {
            model.addAttribute("headerFolder", "fragments/patient-header");
            model.addAttribute("headerName", "patient-header");
        } else {
            model.addAttribute("headerFolder", "fragments/anon-header");
            model.addAttribute("headerName", "anon-header");
        }
    }

}
