package org.pah_monitoring.main.services.auxiliary.auth.interfaces;

import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.entities.users.users.common.User;

public interface AccessRightsCheckService {

    boolean isMainAdministrator();

    boolean isAdministrator();

    boolean isDoctor();

    boolean isPatient();

    boolean isSameUser(User user);

    /*boolean isSameDoctor(Doctor requestedDoctor);*/

    boolean isHospitalUserFromSameHospital(Hospital hospital);

    boolean isAdministratorFromSameHospital(Hospital hospital);

    boolean isOwnDoctor(Patient patient);

}
