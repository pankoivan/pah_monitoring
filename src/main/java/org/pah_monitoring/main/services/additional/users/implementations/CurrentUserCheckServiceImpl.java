package org.pah_monitoring.main.services.additional.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class CurrentUserCheckServiceImpl implements CurrentUserCheckService {

    private CurrentUserExtractionService userExtractionService;

    @Override
    public boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @Override
    public boolean isMainAdministrator() {
        try {
            userExtractionService.mainAdministrator();
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAdministrator() {
        try {
            userExtractionService.administrator();
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isDoctor() {
        try {
            userExtractionService.doctor();
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isPatient() {
        try {
            userExtractionService.patient();
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSelf(User user) {
        try {
            if (!userExtractionService.user().getUserInformation().equals(user.getUserInformation())) {
                return false;
            }
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isHospitalUserFromSameHospital(Hospital hospital) {
        try {
            if (!userExtractionService.hospitalUser().getHospital().equals(hospital)) {
                return false;
            }
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAdministratorFromSameHospital(Hospital hospital) {
        try {
            if (!userExtractionService.administrator().getHospital().equals(hospital)) {
                return false;
            }
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isOwnDoctor(Patient patient) {
        try {
            if (!userExtractionService.doctor().equals(patient.getDoctor())) {
                return false;
            }
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

}
