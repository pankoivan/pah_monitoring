package org.pah_monitoring.main.services.additional.hospitals.interfaces;

import org.pah_monitoring.main.entities.additional.auxiliary.hospitals.AdministratorStatistics;
import org.pah_monitoring.main.entities.additional.auxiliary.hospitals.DoctorStatistics;
import org.pah_monitoring.main.entities.additional.auxiliary.hospitals.PatientStatistics;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;

public interface HospitalUsersStatisticsService {

    AdministratorStatistics getAdministratorStatistics(Hospital hospital);

    DoctorStatistics getDoctorStatistics(Hospital hospital);

    PatientStatistics getPatientStatistics(Hospital hospital);

}
