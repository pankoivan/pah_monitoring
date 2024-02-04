package org.pah_monitoring.main.filtration.filters.users;

import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.filtration.enums.hospitals.HospitalFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.hospitals.HospitalSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/*public class AdministratorFilter implements EntityFilter<Administrator> {

    @Override
    public List<Administrator> apply(List<Administrator> administrators, Map<String, String> parameters, EntityFilter.PageStat pageStat) {
        return
                paged(
                        sorted(
                                filtered(
                                        searched(
                                                administrators, parameters.get("searching")
                                        ), parameters.get("filtration")
                                ), parameters.get("sorting")
                        ), parameters.get("page"),
                        pageStat
                );
    }

    private Stream<Administrator> searched(List<Hospital> hospitals, String searching) {
        return searching == null || searching.isEmpty()
                ? hospitals.stream()
                : hospitals.stream().filter(hospital -> hospital.getName().toLowerCase().contains(searching.toLowerCase()));
    }

    private Stream<Administrator> filtered(Stream<Hospital> hospitals, String filtration) {
        Optional<HospitalFiltrationProperty> filtrationProperty = HospitalFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(hospitalSortingProperty -> switch (hospitalSortingProperty) {
            case WAITING_CODE -> hospitals.filter(Hospital::isWaitingCode);
            case WAITING_REGISTRATION -> hospitals.filter(Hospital::isWaitingRegistration);
            case REGISTERED -> hospitals.filter(Hospital::isRegistered);
        }).orElse(hospitals);
    }

    private Stream<Administrator> sorted(Stream<Hospital> hospitals, String sorting) {
        Optional<HospitalSortingProperty> sortingProperty = HospitalSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(hospitalSortingProperty -> switch (hospitalSortingProperty) {
            case NAME -> hospitals.sorted(Comparator.comparing(Hospital::getName));
            case OID -> hospitals.sorted(Comparator.comparing(Hospital::getOid));
            case DATE -> hospitals.sorted(Comparator.comparing(Hospital::getDate));
        }).orElse(hospitals);
    }

    private List<Administrator> paged(Stream<Hospital> hospitals, String page, PageStat pageStat) {
        List<Hospital> result = hospitals.toList();
        pageStat.setCurrentPage(currentPage(page));
        pageStat.setPagesCount(pagesCount(result));
        return result
                .stream()
                .skip((long) (currentPage(page) - 1) * QuantityRestrictionConstants.MAX_NUMBER_OF_HOSPITALS_PER_PAGE)
                .limit(QuantityRestrictionConstants.MAX_NUMBER_OF_HOSPITALS_PER_PAGE)
                .toList();
    }

    private int currentPage(String page) {
        try {
            return Integer.parseInt(page);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private int pagesCount(List<Hospital> hospitals) {
        return !hospitals.isEmpty()
                ? Math.ceilDiv(hospitals.size(), QuantityRestrictionConstants.MAX_NUMBER_OF_HOSPITALS_PER_PAGE)
                : 1;
    }

}
*/