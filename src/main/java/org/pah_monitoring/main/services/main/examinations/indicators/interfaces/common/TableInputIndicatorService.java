package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

public interface TableInputIndicatorService<T, M, N> extends InputIndicatorService<T, M> {

    N toTablesOutDto();

}
