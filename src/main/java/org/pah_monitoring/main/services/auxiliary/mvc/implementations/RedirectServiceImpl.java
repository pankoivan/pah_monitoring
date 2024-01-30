package org.pah_monitoring.main.services.auxiliary.mvc.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.auxiliary.mvc.interfaces.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class RedirectServiceImpl implements RedirectService {

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

    @Override
    public boolean checkMainRedirect() {
        return !checkService.isAnonymous();
    }

    @Override
    public String mainRedirect() {
        if (checkService.isMainAdministrator()) {
            return "redirect:/hospitals";
        } else if (checkService.isAdministrator()) {
            return "redirect:/hospitals/%s".formatted(extractionService.administrator().getHospital().getId());
        } else if (checkService.isDoctor()) {
            return "redirect:/doctors/%s/patients".formatted(extractionService.doctor().getId());
        } else if (checkService.isPatient()) {
            return "redirect:/patients/%s/indicators".formatted(extractionService.patient().getId());
        } else {
            return "redirect:/login";
        }
    }

    @Override
    public boolean checkNotAnonymousUserRedirect() {
        return !checkService.isAnonymous();
    }

    @Override
    public String notAnonymousUserRedirect() {
        return "redirect:/";
    }

}
