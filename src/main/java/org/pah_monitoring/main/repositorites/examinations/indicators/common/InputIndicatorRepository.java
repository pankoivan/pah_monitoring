package org.pah_monitoring.main.repositorites.examinations.indicators.common;

import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;

import java.util.List;

public interface InputIndicatorRepository<T> {

    List<T> findAllByPatientId(Integer id);

    List<InputIndicator> findAllByPatientIdAbstract(Integer id);

}
