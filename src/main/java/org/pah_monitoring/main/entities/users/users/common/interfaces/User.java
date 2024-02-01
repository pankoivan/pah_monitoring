package org.pah_monitoring.main.entities.users.users.common.interfaces;

import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;

public abstract class User implements BaseEntity {

    public abstract boolean isHospitalUser();

    public abstract boolean isHospitalEmployee();

    public abstract boolean isMainAdministrator();

    public abstract boolean isAdministrator();

    public abstract boolean isDoctor();

    public abstract boolean isPatient();

    public abstract Role getRole();

    public abstract UserSecurityInformation getUserSecurityInformation();

    public abstract UserInformation getUserInformation();

}
