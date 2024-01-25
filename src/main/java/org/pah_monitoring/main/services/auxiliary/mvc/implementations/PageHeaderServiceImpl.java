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
            addHeaderAndUser(model, "fragments/main-admin-header", "main-admin-header");
        } else if (checkService.isAdministrator()) {
            addHeaderAndUser(model, "fragments/admin-header", "admin-header");
        } else if (checkService.isDoctor()) {
            addHeaderAndUser(model, "fragments/doctor-header", "doctor-header");
        } else if (checkService.isPatient()) {
            addHeaderAndUser(model, "fragments/patient-header", "patient-header");
        } else {
            addHeader(model, "fragments/anon-header", "anon-header");
        }
    }

    private void addHeader(Model model, String headerFolder, String headerName) {
        model.addAttribute("headerFolder", headerFolder);
        model.addAttribute("headerName", headerName);
    }

    private void addHeaderAndUser(Model model, String headerFolder, String headerName) {
        addHeader(model, headerFolder, headerName);
        model.addAttribute("currentUser", extractionService.user());
    }

}
