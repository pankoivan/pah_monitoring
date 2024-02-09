package org.pah_monitoring.main.services.additional.users.interfaces;

import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalUser;
import org.pah_monitoring.main.entities.main.users.users.common.User;

public interface CurrentUserExtractionService {

    User user() throws NullPointerException, ClassCastException;

    HospitalUser hospitalUser() throws NullPointerException, ClassCastException;

    HospitalEmployee hospitalEmployee() throws NullPointerException, ClassCastException;

    MainAdministrator mainAdministrator() throws NullPointerException, ClassCastException;

    Administrator administrator() throws NullPointerException, ClassCastException;

    Doctor doctor() throws NullPointerException, ClassCastException;

    Patient patient() throws NullPointerException, ClassCastException;

}
