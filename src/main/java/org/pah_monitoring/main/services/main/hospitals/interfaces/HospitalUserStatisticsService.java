package org.pah_monitoring.main.services.main.hospitals.interfaces;

import org.pah_monitoring.main.entities.additional.hospitals.AdministratorStatistics;
import org.pah_monitoring.main.entities.additional.hospitals.DoctorStatistics;
import org.pah_monitoring.main.entities.additional.hospitals.PatientStatistics;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;

public interface HospitalUserStatisticsService {

    AdministratorStatistics getAdministratorStatistics(Hospital hospital);

    DoctorStatistics getDoctorStatistics(Hospital hospital);

    PatientStatistics getPatientStatistics(Hospital hospital);

}
