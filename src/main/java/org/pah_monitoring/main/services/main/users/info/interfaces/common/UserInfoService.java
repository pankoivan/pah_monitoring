package org.pah_monitoring.main.services.main.users.info.interfaces.common;

import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

public interface UserInfoService<T, M, R, N>
        extends DataAddingValidationService<M>, DataEditingValidationService<R>, DataSavingValidationService<N>, UrlValidationService {

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    T edit(R editingDto) throws DataSavingServiceException;

    void checkAccessRightsForEditing(User userWithRequestedEditingInfo) throws NotEnoughRightsServiceException;

}
