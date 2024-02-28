package org.pah_monitoring.main.services.main.users.inactivity.interfaces.common;

import org.pah_monitoring.main.dto.in.users.inactivity.common.InactivityAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces.Inactivity;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalUser;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

public interface InactivityService<T extends Inactivity, M extends InactivityAddingDto, N extends HospitalUser>
        extends DataAddingValidationService<M> {

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(N hospitalUser) throws NotEnoughRightsServiceException;

}
