package org.pah_monitoring.main.filtration.filters.hospitals;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.filtration.enums.hospitals.HospitalFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.hospitals.HospitalSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("hospitalFilter")
public class HospitalFilter extends AbstractEntityFilter<Hospital> {

    public Stream<Hospital> searched(List<Hospital> hospitals, String searching) {
        return searching == null || searching.isEmpty()
                ? hospitals.stream()
                : hospitals.stream().filter(hospital -> hospital.getName().toLowerCase().contains(searching.toLowerCase()));
    }

    public Stream<Hospital> filtered(Stream<Hospital> hospitals, String filtration) {
        Optional<HospitalFiltrationProperty> filtrationProperty = HospitalFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(hospitalSortingProperty -> switch (hospitalSortingProperty) {
            case WAITING_CODE -> hospitals.filter(Hospital::isWaitingCode);
            case WAITING_REGISTRATION -> hospitals.filter(Hospital::isWaitingRegistration);
            case REGISTERED -> hospitals.filter(Hospital::isRegistered);
        }).orElse(hospitals);
    }

    public Stream<Hospital> sorted(Stream<Hospital> hospitals, String sorting) {
        Optional<HospitalSortingProperty> sortingProperty = HospitalSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(hospitalSortingProperty -> switch (hospitalSortingProperty) {
            case NAME -> hospitals.sorted(Comparator.comparing(Hospital::getName));
            case OID -> hospitals.sorted(Comparator.comparing(Hospital::getOid));
            case DATE -> hospitals.sorted(Comparator.comparing(Hospital::getDate));
        }).orElse(hospitals);
    }

}
