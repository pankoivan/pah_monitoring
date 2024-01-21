package org.pah_monitoring.main.services.users.inactivity.interfaces.common;

import org.pah_monitoring.main.entities.users.users.common.HospitalEmployeeUser;

import java.util.List;

public interface HospitalEmployeeInactivityService<T, M> extends HospitalUserInactivityService<T, M, HospitalEmployeeUser> {

    List<T> findAllByEmployeeInformationId(Integer id);

}
