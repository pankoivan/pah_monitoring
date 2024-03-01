package org.pah_monitoring.main.services.main.hospitals.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.additional.hospitals.AdministratorStatistics;
import org.pah_monitoring.main.entities.additional.hospitals.DoctorStatistics;
import org.pah_monitoring.main.entities.additional.hospitals.PatientStatistics;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalUserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class HospitalUserStatisticsServiceImpl implements HospitalUserStatisticsService {

    @Override
    public AdministratorStatistics getAdministratorStatisticsFor(Hospital hospital) {
        List<Administrator> administrators = hospital.getAdministrators();
        return AdministratorStatistics
                .builder()
                .total(administrators.size())
                .activeCount((int) administrators.stream().filter(Administrator::isActive).count())
                .vacationCount((int) administrators.stream().filter(Administrator::isOnVacation).count())
                .sickLeaveCount((int) administrators.stream().filter(Administrator::isOnSickLeave).count())
                .dismissalCount((int) administrators.stream().filter(Administrator::isDismissed).count())
                .build();
    }

    @Override
    public DoctorStatistics getDoctorStatisticsFor(Hospital hospital) {
        List<Doctor> doctors = hospital.getDoctors();
        return DoctorStatistics
                .builder()
                .total(doctors.size())
                .activeCount((int) doctors.stream().filter(Doctor::isActive).count())
                .vacationCount((int) doctors.stream().filter(Doctor::isOnVacation).count())
                .sickLeaveCount((int) doctors.stream().filter(Doctor::isOnSickLeave).count())
                .dismissalCount((int) doctors.stream().filter(Doctor::isDismissed).count())
                .withoutPatientsCount((int) doctors.stream().filter(Doctor::hasNoPatients).count())
                .build();
    }

    @Override
    public PatientStatistics getPatientStatisticsFor(Hospital hospital) {
        List<Patient> patients = hospital.getPatients();
        return PatientStatistics
                .builder()
                .total(patients.size())
                .activeCount((int) patients.stream().filter(Patient::isActive).count())
                .inactiveCount((int) patients.stream().filter(Patient::isNotActive).count())
                .unallocated((int) patients.stream().filter(Patient::hasNoDoctor).count())
                .build();
    }

}
