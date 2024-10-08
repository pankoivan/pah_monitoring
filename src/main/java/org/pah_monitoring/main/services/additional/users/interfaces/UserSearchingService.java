package org.pah_monitoring.main.services.additional.users.interfaces;

import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

public interface UserSearchingService extends UrlValidationService {

    User findUserByUserSecurityInformationId(Integer userSecurityInformationId) throws DataSearchingServiceException;

    User findUserByUserInformationId(Integer userInformationId) throws DataSearchingServiceException;

    HospitalEmployee findHospitalEmployeeByEmployeeInformationId(Integer employeeInformationId) throws DataSearchingServiceException;

}
