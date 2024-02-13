package org.pah_monitoring.main.services.additional.mvc.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.services.additional.mvc.interfaces.RedirectService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class RedirectServiceImpl implements RedirectService {

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    @Override
    public boolean checkMainRedirect() {
        return checkService.isNotAnonymous();
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
        return checkService.isNotAnonymous();
    }

    @Override
    public String notAnonymousUserRedirect() {
        return "redirect:/";
    }

    @Override
    public boolean checkPatientAnamnesisRedirect() {
        return extractionService.patient().hasAnamnesis();
    }

    @Override
    public String patientAnamnesisRedirect() {
        return "/anamnesis/for/%s".formatted(extractionService.patient().getId());
    }

}
