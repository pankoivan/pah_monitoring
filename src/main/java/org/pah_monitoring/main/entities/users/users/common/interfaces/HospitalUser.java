package org.pah_monitoring.main.entities.users.users.common.interfaces;

import org.pah_monitoring.main.entities.hospitals.Hospital;

public abstract class HospitalUser extends User {

    public abstract Hospital getHospital();

    public abstract boolean isActive();

    public abstract boolean isNotActive();

    public abstract String getActivityMessage();

}
