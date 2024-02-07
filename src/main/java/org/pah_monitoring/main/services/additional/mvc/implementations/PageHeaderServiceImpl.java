package org.pah_monitoring.main.services.additional.mvc.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class PageHeaderServiceImpl implements PageHeaderService {

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    @Override
    public void addHeader(Model model) {
        if (checkService.isMainAdministrator()) {
            addHeaderAndUser(model, "fragments/headers/main-admin-header", "main-admin-header");
        } else if (checkService.isAdministrator()) {
            addHeaderAndUser(model, "fragments/headers/admin-header", "admin-header");
        } else if (checkService.isDoctor()) {
            addHeaderAndUser(model, "fragments/headers/doctor-header", "doctor-header");
        } else if (checkService.isPatient()) {
            addHeaderAndUser(model, "fragments/headers/patient-header", "patient-header");
        } else {
            addHeader(model, "fragments/headers/anon-header", "anon-header");
        }
    }

    private void addHeader(Model model, String headerPath, String headerName) {
        model.addAttribute("headerPath", headerPath);
        model.addAttribute("headerName", headerName);
    }

    private void addHeaderAndUser(Model model, String headerPath, String headerName) {
        addHeader(model, headerPath, headerName);
        model.addAttribute("currentUser", extractionService.user());
    }

}
