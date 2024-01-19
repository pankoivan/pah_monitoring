package org.pah_monitoring.main.services.users.users.interfaces.common;

import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.UrlValidationService;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface HospitalUserService<T, M, R, N> extends UrlValidationService, SavingValidationService<N> {

    List<T> findAll();

    void checkAccessForObtainingUser(T requestedUser) throws NotEnoughRightsServiceException;

    T findById(Integer id) throws DataSearchingServiceException;

    void checkAccessForObtainingHospitalUsers(Hospital requestedHospital) throws NotEnoughRightsServiceException;

    List<T> findAllByHospitalId(Integer hospitalId) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    T edit(R editingDto) throws DataSearchingServiceException, DataSavingServiceException;

    void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException;

    void checkAccessForEditing(T requestedEditingUser) throws NotEnoughRightsServiceException;

    void checkDataValidityForEditing(R editingDto, BindingResult bindingResult) throws DataSearchingServiceException, DataValidationServiceException;

}
