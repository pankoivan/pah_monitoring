package org.pah_monitoring.main.services.main.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.additional.dto.saving.users.inactivity.SickLeaveAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.saving.DataAddingValidationService;

import java.util.List;

public interface SickLeaveService extends DataAddingValidationService<SickLeaveAddingDto> {

    List<SickLeave> findAllByEmployeeInformationId(Integer id);

    SickLeave add(SickLeaveAddingDto addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(HospitalEmployee hospitalEmployee) throws NotEnoughRightsServiceException;

}
