package org.pah_monitoring.main.services.auxiliary.access.interfaces;

import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;

public interface AccessRightsCheckService {

    boolean isMainAdministrator();

    boolean isAdministrator();

    boolean isDoctor();

    boolean isPatient();

    boolean isSameUser(User user);

    boolean isSameDoctor(Doctor doctor);

    boolean isSamePatient(Patient patient);

    boolean isHospitalUserFromSameHospital(Hospital hospital);

    boolean isAdministratorFromSameHospital(Hospital hospital);

    boolean isOwnDoctor(Patient patient);

}
