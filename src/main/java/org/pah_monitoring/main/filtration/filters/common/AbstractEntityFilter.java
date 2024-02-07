package org.pah_monitoring.main.filtration.filters.common;

import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractEntityFilter<T> implements EntityFilter<T> {

    @Override
    public List<T> apply(List<T> entities, Map<String, String> parameters, PageStat pageStat) {
        return
                paged(
                        sorted(
                                filtered(
                                        searched(
                                                entities, parameters.get("searching")
                                        ), parameters.get("filtration")
                                ), parameters.get("sorting")
                        ), parameters.get("page"),
                        pageStat
                );
    }

    public abstract Stream<T> searched(List<T> entities, String searching);

    public abstract Stream<T> filtered(Stream<T> entities, String filtration);

    public abstract Stream<T> sorted(Stream<T> entities, String sorting);

    public List<T> paged(Stream<T> entities, String page, PageStat pageStat) {
        List<T> result = entities.toList();
        pageStat.setCurrentPage(currentPage(page));
        pageStat.setPagesCount(pagesCount(result));
        return result
                .stream()
                .skip((long) (currentPage(page) - 1) * QuantityRestrictionConstants.MAX_NUMBER_OF_ELEMENTS_PER_PAGE)
                .limit(QuantityRestrictionConstants.MAX_NUMBER_OF_ELEMENTS_PER_PAGE)
                .toList();
    }

    private int currentPage(String page) {
        try {
            return Integer.parseInt(page);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private int pagesCount(List<T> entities) {
        return !entities.isEmpty()
                ? Math.ceilDiv(entities.size(), QuantityRestrictionConstants.MAX_NUMBER_OF_ELEMENTS_PER_PAGE)
                : 1;
    }

}
