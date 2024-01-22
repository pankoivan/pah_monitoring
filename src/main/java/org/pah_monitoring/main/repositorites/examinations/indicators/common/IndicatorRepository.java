package org.pah_monitoring.main.repositorites.examinations.indicators.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndicatorRepository<T> extends JpaRepository<T, Integer> {

    List<T> findAllByPatientId(Integer id);

}
