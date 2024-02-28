package org.pah_monitoring.main.entities.main.users.users.common.interfaces;

import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;

public interface User extends BaseEntity {

    boolean isActive();

    boolean isNotActive();

    boolean isHospitalUser();

    boolean isHospitalEmployee();

    boolean isMainAdministrator();

    boolean isAdministrator();

    boolean isDoctor();

    boolean isPatient();

    Role getRole();

    UserSecurityInformation getUserSecurityInformation();

    UserInformation getUserInformation();

}
