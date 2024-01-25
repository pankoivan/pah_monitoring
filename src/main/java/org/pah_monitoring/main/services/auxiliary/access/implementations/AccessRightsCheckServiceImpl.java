package org.pah_monitoring.main.services.auxiliary.access.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AccessRightsCheckServiceImpl implements AccessRightsCheckService {

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
    public boolean isSameUser(User user) {
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
    public boolean isSameDoctor(Doctor doctor) {
        try {
            if (!userExtractionService.doctor().equals(doctor)) {
                return false;
            }
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSamePatient(Patient patient) {
        try {
            if (!userExtractionService.patient().equals(patient)) {
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
