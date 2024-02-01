package org.pah_monitoring.main.entities.main.users.users.common.interfaces;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;

public abstract class HospitalUser extends User {

    public abstract Hospital getHospital();

    public abstract boolean isActive();

    public abstract boolean isNotActive();

    public abstract String getActivityMessage();

}
