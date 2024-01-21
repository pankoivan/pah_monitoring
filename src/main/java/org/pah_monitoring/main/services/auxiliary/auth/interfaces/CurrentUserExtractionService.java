package org.pah_monitoring.main.services.auxiliary.auth.interfaces;

import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;

public interface CurrentUserExtractionService {

    User user() throws NullPointerException, ClassCastException;

    HospitalUser hospitalUser() throws NullPointerException, ClassCastException;

    HospitalEmployee hospitalEmployeeUser() throws NullPointerException, ClassCastException;

    MainAdministrator mainAdministrator() throws NullPointerException, ClassCastException;

    Administrator administrator() throws NullPointerException, ClassCastException;

    Doctor doctor() throws NullPointerException, ClassCastException;

    Patient patient() throws NullPointerException, ClassCastException;

}
