package org.pah_monitoring.main.entities.main.users.users.common;

import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;

public abstract class User implements BaseEntity {

    public abstract boolean isActive();

    public abstract boolean isNotActive();

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
