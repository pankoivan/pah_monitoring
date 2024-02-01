package org.pah_monitoring.main.services.additional.users.interfaces;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;

public interface CurrentUserCheckService {

    boolean isAnonymous();

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
