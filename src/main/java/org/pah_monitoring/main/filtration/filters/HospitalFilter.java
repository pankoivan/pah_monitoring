package org.pah_monitoring.main.filtration.filters;

import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.filtration.enums.HospitalFiltrationProperty;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component("hospitalFilter")
public class HospitalFilter implements EntityFilter<Hospital> {

    @Override
    public List<Hospital> apply(List<Hospital> hospitals, Map<String, String> parameters) {
        return paged(
                sorted(
                        filtered(
                                searched(
                                        hospitals, parameters.get("searching")
                                ), parameters.get("filtration")
                        ), parameters.get("sorting")
                ), parameters.get("page")
        );
    }

    private Stream<Hospital> searched(List<Hospital> hospitals, String searching) {

    }

    private Stream<Hospital> filtered(Stream<Hospital> hospitals, String filtration) {
        Optional<Predicate<Hospital>> predicate = filtration(filtration);
        return predicate.isEmpty()
                ? hospitals
                : hospitals.filter(predicate.get());
    }

    private Stream<Hospital> sorted(Stream<Hospital> hospitals, String sorting) {

    }

    private List<Hospital> paged(Stream<Hospital> hospitals, String page) {
        return hospitals
                .skip((long) (page(page) - 1) * QuantityRestrictionConstants.MAX_NUMBER_OF_HOSPITALS_PER_PAGE)
                .limit(QuantityRestrictionConstants.MAX_NUMBER_OF_HOSPITALS_PER_PAGE)
                .toList();
    }

    private Optional<Predicate<Hospital>> filtration(String filtration) {
        HospitalFiltrationProperty filtrationProperty;
        try {
            filtrationProperty = HospitalFiltrationProperty.valueOf(filtration);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        Predicate<Hospital> predicate = switch (filtrationProperty) {
            case WAITING_CODE -> Hospital::isWaitingCode;
            case WAITING_REGISTRATION -> Hospital::isWaitingRegistration;
            case REGISTERED -> Hospital::isRegistered;
        };
        return Optional.of(predicate);
    }

    private int page(String page) {
        try {
            return Integer.parseInt(page);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

}
