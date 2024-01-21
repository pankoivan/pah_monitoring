package org.pah_monitoring.main.services.users.inactivity.interfaces.common;

import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;

import java.util.List;

public interface HospitalEmployeeInactivityService<T, M> extends HospitalUserInactivityService<T, M, HospitalEmployee> {

    List<T> findAllByEmployeeInformationId(Integer id);

}
