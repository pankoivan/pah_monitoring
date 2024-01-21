package org.pah_monitoring.main.services.users.inactivity.interfaces.common;

import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;

public interface HospitalUserInactivityService<T, M, N> extends DataAddingValidationService<M> {

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(N entity) throws NotEnoughRightsServiceException;

}
