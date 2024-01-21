package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.VacationAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.Vacation;
import org.pah_monitoring.main.services.users.inactivity.interfaces.common.HospitalEmployeeInactivityService;

public interface VacationService extends HospitalEmployeeInactivityService<Vacation, VacationAddingDto> {

}
