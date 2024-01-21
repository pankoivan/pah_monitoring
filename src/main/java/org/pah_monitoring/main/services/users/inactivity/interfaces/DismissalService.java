package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.DismissalAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.Dismissal;
import org.pah_monitoring.main.services.users.inactivity.interfaces.common.HospitalEmployeeInactivityService;

public interface DismissalService extends HospitalEmployeeInactivityService<Dismissal, DismissalAddingDto> {

}
