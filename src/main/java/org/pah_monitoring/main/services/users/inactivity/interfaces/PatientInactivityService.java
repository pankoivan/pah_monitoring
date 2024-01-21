package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.PatientInactivityAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.PatientInactivity;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.services.users.inactivity.interfaces.common.HospitalUserInactivityService;

public interface PatientInactivityService extends HospitalUserInactivityService<PatientInactivity, PatientInactivityAddingDto, Patient> {

}
