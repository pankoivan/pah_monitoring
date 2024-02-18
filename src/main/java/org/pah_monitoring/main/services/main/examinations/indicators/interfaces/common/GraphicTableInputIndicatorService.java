package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

public interface GraphicTableInputIndicatorService<T, M, N, R> extends TableInputIndicatorService<T, M, N> {

    R toGraphicsOutDto();

}
