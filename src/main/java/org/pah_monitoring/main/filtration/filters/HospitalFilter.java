package org.pah_monitoring.main.filtration.filters;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class HospitalFilter implements EntityFilter<Hospital> {

    @Override
    public List<Hospital> apply(List<Hospital> hospitals, Map<String, String> parameters) {
        return paged(
                sorted(
                        filtered(
                                searched(
                                        hospitals, "searching"
                                ), parameters.get("filtration")
                        ), parameters.get("sorting")
                ), parameters.get("page")
        );
    }

    private Stream<Hospital> searched(List<Hospital> hospitals, String searching) {

    }

    private Stream<Hospital> filtered(Stream<Hospital> hospitals, String filtration) {

    }

    private Stream<Hospital> sorted(Stream<Hospital> hospitals, String sorting) {

    }

    private List<Hospital> paged(Stream<Hospital> hospitals, String page) {

    }

}
