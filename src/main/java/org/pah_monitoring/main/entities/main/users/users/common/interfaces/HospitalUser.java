package org.pah_monitoring.main.entities.main.users.users.common.interfaces;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces.Inactivity;

import java.util.Optional;

public interface HospitalUser extends User {

    Hospital getHospital();

    Optional<Inactivity> getCurrentInactivity();

}
