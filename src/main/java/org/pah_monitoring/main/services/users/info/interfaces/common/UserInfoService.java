package org.pah_monitoring.main.services.users.info.interfaces.common;

import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;
import org.springframework.validation.BindingResult;

public interface UserInfoService<T, M, R, N> extends DataSavingValidationService<N> {

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessForEditing(T requestedEditingInfo) throws NotEnoughRightsServiceException;

    T edit(R editingDto) throws DataSavingServiceException;

    void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException;

    void checkDataValidityForEditing(R editingDto, BindingResult bindingResult) throws DataValidationServiceException;

}
