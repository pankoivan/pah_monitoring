package org.pah_monitoring.main.filtration.filters.users;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.filtration.enums.hospitals.HospitalFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.AdministratorFiltrationProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AdministratorFilter extends AbstractEntityFilter<Administrator> {

    @Override
    protected Stream<Administrator> searched(List<Administrator> administrators, String searching) {
        return searching == null || searching.isEmpty()
                ? administrators.stream()
                : administrators.stream().filter(
                        administrator -> administrator.getUserInformation().getFullName().toLowerCase().contains(searching.toLowerCase())
                );
    }

    @Override
    protected Stream<Administrator> filtered(Stream<Administrator> administrators, String filtration) {
        Optional<AdministratorFiltrationProperty> filtrationProperty = AdministratorFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(administratorFiltrationProperty -> switch (administratorFiltrationProperty) {
            case ACTIVE -> administrators.filter(Administrator::getCurrentInactivity)
            case VACATION -> administrators.filter(Administrator::getCurrentInactivity)
            case SICK_LEAVE -> administrators.filter(Administrator::getCurrentInactivity)
            case DISMISSAL -> administrators.filter(Administrator::getCurrentInactivity)
        }).orElse(hospitals);
    }

    @Override
    protected Stream<Administrator> sorted(Stream<Administrator> administrators, String sorting) {
        return null;
    }

}
