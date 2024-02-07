package org.pah_monitoring.main.filtration.filters.users;

import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.filtration.enums.users.DoctorFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.DoctorSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("doctorFilter")
public class DoctorFilter extends AbstractEntityFilter<Doctor> {

    @Override
    public Stream<Doctor> searched(List<Doctor> doctors, String searching) {
        return searching == null || searching.isEmpty()
                ? doctors.stream()
                : doctors.stream().filter(
                        doctor -> doctor.getUserInformation().getFullName().toLowerCase().contains(searching.toLowerCase())
                );
    }

    @Override
    public Stream<Doctor> filtered(Stream<Doctor> doctors, String filtration) {
        Optional<DoctorFiltrationProperty> filtrationProperty = DoctorFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(doctorFiltrationProperty -> switch (doctorFiltrationProperty) {
            case ACTIVE -> doctors.filter(Doctor::isActive);
            case VACATION -> doctors.filter(Doctor::isOnVacation);
            case SICK_LEAVE -> doctors.filter(Doctor::isOnSickLeave);
            case DISMISSAL -> doctors.filter(Doctor::isDismissed);
            case HAS_PATIENTS -> doctors.filter(Doctor::hasPatients);
            case HAS_NO_PATIENTS -> doctors.filter(Doctor::hasNoPatients);
        }).orElse(doctors);
    }

    @Override
    public Stream<Doctor> sorted(Stream<Doctor> doctors, String sorting) {
        Optional<DoctorSortingProperty> sortingProperty = DoctorSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(doctorSortingProperty -> switch (doctorSortingProperty) {
            case FULL_NAME -> doctors.sorted(Comparator.comparing(doctor -> doctor.getUserInformation().getFullName()));
            case PHONE_NUMBER -> doctors.sorted(Comparator.comparing(doctor -> doctor.getUserInformation().getPhoneNumber()));
            case PATIENTS_COUNT -> doctors.sorted(Comparator.comparingInt(Doctor::patientsCount));
        }).orElse(doctors);
    }

}
