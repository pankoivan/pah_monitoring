package org.pah_monitoring.main.services.auxiliary.users.interfaces;

import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;

public interface UserSearchingService {

    User findUserByUserSecurityInformationId(Integer id) throws DataSearchingServiceException;

    User findUserByUserInformationId(Integer id) throws DataSearchingServiceException;

    HospitalEmployee findHospitalEmployeeByEmployeeInformationId(Integer id) throws DataSearchingServiceException;

}
