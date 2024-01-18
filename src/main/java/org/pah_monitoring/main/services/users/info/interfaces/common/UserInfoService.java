package org.pah_monitoring.main.services.users.info.interfaces.common;

import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.springframework.validation.BindingResult;

public interface UserInfoService<T, M, R> {

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M m) throws DataSavingServiceException;

    T edit(R r) throws DataSearchingServiceException, DataSavingServiceException;

    void checkDataValidityForEditing(R r, BindingResult bindingResult) throws DataSearchingServiceException, DataValidationServiceException;

}
