package org.pah_monitoring.main.entities.users.users.common;

import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;

public interface User extends BaseEntity {

    UserSecurityInformation getUserSecurityInformation();

    UserInformation getUserInformation();

}
