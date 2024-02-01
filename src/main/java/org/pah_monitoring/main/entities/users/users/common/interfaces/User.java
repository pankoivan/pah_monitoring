package org.pah_monitoring.main.entities.users.users.common.interfaces;

import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;

public interface User extends BaseEntity {

    UserSecurityInformation getUserSecurityInformation();

    UserInformation getUserInformation();

    Role getRole();

    boolean isHospitalEmployee();

}
