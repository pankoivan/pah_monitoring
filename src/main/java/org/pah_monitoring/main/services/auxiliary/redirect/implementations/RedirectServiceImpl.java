package org.pah_monitoring.main.services.auxiliary.redirect.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.auxiliary.redirect.interfaces.RedirectService;
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
        } else { // todo: maybe change to else-if
            return "redirect:/indicators";
        }
    }

}
