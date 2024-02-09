package org.pah_monitoring.main.entities.main.users.users.common;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.inactivity.common.Inactivity;

import java.util.Optional;

public abstract class HospitalUser extends User {

    public abstract Hospital getHospital();

    public abstract Optional<Inactivity> getCurrentInactivity();

}
