package org.pah_monitoring.main.entities.users.users.common;

import org.pah_monitoring.main.entities.hospitals.Hospital;

public interface HospitalUser extends User {

    Hospital getHospital();

}
