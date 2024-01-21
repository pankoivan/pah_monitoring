package org.pah_monitoring.main.services.users.inactivity.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.inactivity.adding.InactivePatientAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.InactivePatient;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.services.users.inactivity.interfaces.common.HospitalUserInactivityService;

public interface InactivePatientService extends HospitalUserInactivityService<InactivePatient, InactivePatientAddingDto, Patient> {

}
