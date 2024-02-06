package org.pah_monitoring.main.dto.in.users.users.common.interfaces;

import org.pah_monitoring.main.dto.in.users.info.security.UserSecurityInformationAddingDto;

public interface HospitalUserAddingInfo {

    String getCode();

    UserSecurityInformationAddingDto getUserSecurityInformationAddingDto();

}
