package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

public interface GraphicsTablesInputIndicatorService<T, M, N, R> extends TablesInputIndicatorService<T, M, N> {

    R toGraphicsOutDto();

}
