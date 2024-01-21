package org.pah_monitoring.main.services.users.inactivity.interfaces.common;

import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;

public interface HospitalUserInactivityService<T, M, N extends HospitalUser> extends DataAddingValidationService<M> {

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(N hospitalUser) throws NotEnoughRightsServiceException;

}
