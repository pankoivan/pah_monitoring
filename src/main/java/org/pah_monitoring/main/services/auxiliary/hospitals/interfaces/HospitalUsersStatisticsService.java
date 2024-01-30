package org.pah_monitoring.main.services.auxiliary.hospitals.interfaces;

import org.pah_monitoring.main.entities.auxiliary.hospitals.AdministratorStatistics;
import org.pah_monitoring.main.entities.auxiliary.hospitals.DoctorStatistics;
import org.pah_monitoring.main.entities.auxiliary.hospitals.PatientStatistics;
import org.pah_monitoring.main.entities.hospitals.Hospital;

public interface HospitalUsersStatisticsService {

    AdministratorStatistics getAdministratorStatistics(Hospital hospital);

    DoctorStatistics getDoctorStatistics(Hospital hospital);

    PatientStatistics getPatientStatistics(Hospital hospital);

}
