package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.DismissalAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;

public interface DismissalService extends DataAddingValidationService<DismissalAddingDto> {

    Dismissal add(DismissalAddingDto addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(HospitalEmployee hospitalEmployee) throws NotEnoughRightsServiceException;

}
