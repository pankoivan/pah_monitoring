package org.pah_monitoring.main.services.auxiliary.auth.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.common.User;
import org.pah_monitoring.main.services.auxiliary.auth.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.auth.interfaces.CurrentUserExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AccessRightsCheckServiceImpl implements AccessRightsCheckService {

    private CurrentUserExtractionService userExtractionService;

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
            if (!userExtractionService.user().getId().equals(user.getId())) {
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
            if (!userExtractionService.doctor().getId().equals(doctor.getId())) {
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

}
