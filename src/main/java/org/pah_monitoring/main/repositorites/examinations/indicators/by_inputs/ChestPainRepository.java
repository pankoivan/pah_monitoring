package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.ChestPain;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.IndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChestPainRepository extends JpaRepository<ChestPain, Integer>, IndicatorRepository<ChestPain> {

}
