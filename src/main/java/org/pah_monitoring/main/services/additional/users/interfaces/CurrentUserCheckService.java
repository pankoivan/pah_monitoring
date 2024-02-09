package org.pah_monitoring.main.services.additional.users.interfaces;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.entities.main.users.users.common.User;

public interface CurrentUserCheckService {

    boolean isAnonymous();

    boolean isNotAnonymous();

    boolean isMainAdministrator();

    boolean isAdministrator();

    boolean isDoctor();

    boolean isPatient();

    boolean isSelf(User user);

    boolean isHospitalUserFromSameHospital(Hospital hospital);

    boolean isAdministratorFromSameHospital(Hospital hospital);

    boolean isOwnDoctor(Patient patient);

}
