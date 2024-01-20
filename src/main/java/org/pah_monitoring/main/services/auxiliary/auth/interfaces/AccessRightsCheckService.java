package org.pah_monitoring.main.services.auxiliary.auth.interfaces;

import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.common.User;

public interface AccessRightsCheckService {

    boolean isMainAdministrator();

    boolean isAdministrator();

    boolean isDoctor();

    boolean isPatient();

    boolean isSameUser(User user);

    boolean isSameDoctor(Doctor requestedDoctor);

    boolean isHospitalUserFromSameHospital(Hospital hospital);

    boolean isAdministratorFromSameHospital(Hospital hospital);

}
