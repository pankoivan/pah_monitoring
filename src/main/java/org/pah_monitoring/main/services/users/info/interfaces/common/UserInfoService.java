package org.pah_monitoring.main.services.users.info.interfaces.common;

import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;

public interface UserInfoService<T, M, R, N>
        extends DataAddingValidationService<M>, DataEditingValidationService<R>, DataSavingValidationService<N> {

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    T edit(R editingDto) throws DataSavingServiceException;

    void checkAccessRightsForEditing(User userWithRequestedEditingInfo) throws NotEnoughRightsServiceException;

}
