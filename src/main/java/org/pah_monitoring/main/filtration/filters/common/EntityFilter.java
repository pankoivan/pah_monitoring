package org.pah_monitoring.main.filtration.filters.common;

import lombok.*;

import java.util.List;
import java.util.Map;

public interface EntityFilter<T> {

    List<T> apply(List<T> entities, Map<String, String[]> parameters, PageStat pageStat);

    @NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
    class PageStat {
        private int currentPage;
        private int pagesCount;
    }

}
