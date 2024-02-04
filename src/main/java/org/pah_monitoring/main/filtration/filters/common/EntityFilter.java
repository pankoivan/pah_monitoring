package org.pah_monitoring.main.filtration.filters.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

public interface EntityFilter<T> {

    List<T> apply(List<T> entities, Map<String, String[]> parameters, PageStat pageStat);

    @Data
    @Builder
    class PageStat {
        private int currentPage;
        private int pagesCount;
    }

}
