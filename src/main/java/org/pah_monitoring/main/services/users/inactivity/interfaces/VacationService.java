package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.VacationAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;

import java.util.List;

public interface VacationService extends DataAddingValidationService<VacationAddingDto> {

    List<Vacation> findAllByEmployeeInformationId(Integer id);

    Vacation add(VacationAddingDto addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(HospitalEmployee hospitalEmployee) throws NotEnoughRightsServiceException;

}
