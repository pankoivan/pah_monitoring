package org.pah_monitoring.main.services.users.users.interfaces.common;

import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.validation.interfaces.UrlValidationService;

import java.util.List;

public interface UserService<T, M, R> extends UrlValidationService {

    List<T> findAll();

    T findById(Integer id) throws DataSearchingServiceException;

    List<T> findAllByHospitalId(Integer id) throws DataSearchingServiceException;

    T add(M m) throws DataSavingServiceException;

    T edit(R r) throws DataSearchingServiceException, DataSavingServiceException;

    void checkDataValidityForAdding(M m) throws DataValidationServiceException;

    void checkDataValidityForEditing(R r) throws DataSearchingServiceException, DataValidationServiceException;

}
