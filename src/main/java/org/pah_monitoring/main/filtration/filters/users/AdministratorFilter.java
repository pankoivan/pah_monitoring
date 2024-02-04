package org.pah_monitoring.main.filtration.filters.users;

import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.filtration.enums.users.AdministratorFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.AdministratorSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("administratorFilter")
public class AdministratorFilter extends AbstractEntityFilter<Administrator> {

    @Override
    public Stream<Administrator> searched(List<Administrator> administrators, String searching) {
        return searching == null || searching.isEmpty()
                ? administrators.stream()
                : administrators.stream().filter(
                        administrator -> administrator.getUserInformation().getFullName().toLowerCase().contains(searching.toLowerCase())
                );
    }

    @Override
    public Stream<Administrator> filtered(Stream<Administrator> administrators, String filtration) {
        Optional<AdministratorFiltrationProperty> filtrationProperty = AdministratorFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(administratorFiltrationProperty -> switch (administratorFiltrationProperty) {
            case ACTIVE -> administrators.filter(Administrator::isActive);
            case VACATION -> administrators.filter(Administrator::isOnVacation);
            case SICK_LEAVE -> administrators.filter(Administrator::isOnSickLeave);
            case DISMISSAL -> administrators.filter(Administrator::isDismissed);
        }).orElse(administrators);
    }

    @Override
    public Stream<Administrator> sorted(Stream<Administrator> administrators, String sorting) {
        Optional<AdministratorSortingProperty> sortingProperty = AdministratorSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(administratorSortingProperty -> switch (administratorSortingProperty) {
            case FULL_NAME -> administrators.sorted(Comparator.comparing(administrator -> administrator.getUserInformation().getFullName()));
            case PHONE_NUMBER -> administrators.sorted(Comparator.comparing(administrator -> administrator.getUserInformation().getPhoneNumber()));
        }).orElse(administrators);
    }

}
