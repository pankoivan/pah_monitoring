package org.pah_monitoring.main.services.main.hospitals.interfaces;

import org.pah_monitoring.main.entities.additional.hospitals.AdministratorStatistics;
import org.pah_monitoring.main.entities.additional.hospitals.DoctorStatistics;
import org.pah_monitoring.main.entities.additional.hospitals.PatientStatistics;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;

public interface HospitalUserStatisticsService {

    AdministratorStatistics getAdministratorStatisticsFor(Hospital hospital);

    DoctorStatistics getDoctorStatisticsFor(Hospital hospital);

    PatientStatistics getPatientStatisticsFor(Hospital hospital);

}
