package org.pah_monitoring.main.filtration.filters.users;

import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.filtration.enums.users.PatientFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.PatientSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("patientFilter")
public class PatientFilter extends AbstractEntityFilter<Patient> {

    @Override
    public Stream<Patient> searched(List<Patient> patients, String searching) {
        return searching == null || searching.isEmpty()
                ? patients.stream()
                : patients.stream()
                    .filter(patient -> patient.getUserInformation().getFullName().toLowerCase().contains(searching.toLowerCase()));
    }

    @Override
    public Stream<Patient> filtered(Stream<Patient> patients, String filtration) {
        Optional<PatientFiltrationProperty> filtrationProperty = PatientFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(patientFiltrationProperty -> switch (patientFiltrationProperty) {
            case ACTIVE -> patients.filter(Patient::isActive);
            case INACTIVE -> patients.filter(Patient::isNotActive);
            case HAS_DOCTOR -> patients.filter(Patient::hasDoctor);
            case HAS_NO_DOCTOR -> patients.filter(Patient::hasNoDoctor);
        }).orElse(patients);
    }

    @Override
    public Stream<Patient> sorted(Stream<Patient> patients, String sorting) {
        Optional<PatientSortingProperty> sortingProperty = PatientSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(patientSortingProperty -> switch (patientSortingProperty) {
            case FULL_NAME -> patients.sorted(Comparator.comparing(patient -> patient.getUserInformation().getFullName()));
            case PHONE_NUMBER -> patients.sorted(Comparator.comparing(patient -> patient.getUserInformation().getPhoneNumber()));
        }).orElse(patients);
    }

}
