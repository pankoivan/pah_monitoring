package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.SickLeaveAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.SickLeave;
import org.pah_monitoring.main.services.users.inactivity.interfaces.common.HospitalEmployeeInactivityService;

public interface SickLeaveService extends HospitalEmployeeInactivityService<SickLeave, SickLeaveAddingDto> {

}
