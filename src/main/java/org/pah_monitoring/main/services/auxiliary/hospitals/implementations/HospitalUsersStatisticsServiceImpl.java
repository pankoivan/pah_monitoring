package org.pah_monitoring.main.services.auxiliary.hospitals.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.hospitals.AdministratorStatistics;
import org.pah_monitoring.main.entities.auxiliary.hospitals.DoctorStatistics;
import org.pah_monitoring.main.entities.auxiliary.hospitals.PatientStatistics;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.services.auxiliary.hospitals.interfaces.HospitalUsersStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class HospitalUsersStatisticsServiceImpl implements HospitalUsersStatisticsService {

    @Override
    public AdministratorStatistics getAdministratorStatistics(Hospital hospital) {
        return null;
    }

    @Override
    public DoctorStatistics getDoctorStatistics(Hospital hospital) {
        return null;
    }

    @Override
    public PatientStatistics getPatientStatistics(Hospital hospital) {
        return null;
    }

}
